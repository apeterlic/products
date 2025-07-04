package dev.beenary.core.order;

import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.create.CreateOrderResponse;
import dev.beenary.api.order.read.GetOrderRequest;
import dev.beenary.api.order.read.GetOrderResponse;
import dev.beenary.api.order.read.SearchOrderRequest;
import dev.beenary.api.order.read.SearchOrderResponse;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.common.utility.Defense;
import dev.beenary.common.utility.SortDirection;
import dev.beenary.core.product.ProductAuditService;
import dev.beenary.persistence.order.OrderDb;
import dev.beenary.persistence.order.OrderRepository;
import dev.beenary.persistence.product.ProductDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static dev.beenary.core.order.OrderValidator.ERROR_PRODUCT_NOT_FOUND;

@Service
public class InternalOrderService implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderRepository repository;

    private final OrderValidator validator;

    private final ProductAuditService productAuditService;

    InternalOrderService(final OrderRepository repository, final OrderValidator validator, final ProductAuditService productAuditService) {
        this.repository = Defense.notNull(repository, OrderRepository.class.getSimpleName());
        this.validator = Defense.notNull(validator, OrderValidator.class.getSimpleName());
        this.productAuditService = Defense.notNull(productAuditService, ProductAuditService.class.getSimpleName());
    }

    @Override
    public GetOrderResponse get(final GetOrderRequest request) {
        logger.debug("get() >> orderId {}", request.getId());
        final OrderDb orderDb = repository.getReferenceById(validator.validate(request));
        findCorrectProductRevision(orderDb);
        return new GetOrderResponse(OrderMapper.apiMapper().toDto(orderDb));
    }

    @Override
    public CreateOrderResponse create(final CreateOrderRequest request) {
        final OrderDb orderDb = repository.save(validator.validate(request));
        logger.debug("create() << orderId {}", orderDb.getId());
        return new CreateOrderResponse(OrderMapper.apiMapper().toDto(orderDb));
    }

    @Override
    public SearchOrderResponse search(final SearchOrderRequest request) {
        final Sort sort = SortDirection.DESC.equals(request.getSortingFilter().getSortDirection())
                ? Sort.by(request.getSortingFilter().getColumn()).descending()
                : Sort.by(request.getSortingFilter().getColumn()).ascending();
        final Pageable pageable = PageRequest.of(request.getPaginationFilter().getPage() - 1,
                request.getPaginationFilter().getEntitiesPerPage(), sort);

        // get all orders from requested period
        final Page<OrderDb> orders = repository.findByCreatedAtBetween(request.getCreatedFilter().getFrom(),
                request.getCreatedFilter().getTo(),
                pageable);

        orders.stream().forEach(this::findCorrectProductRevision);

        return new SearchOrderResponse(OrderMapper.apiMapper().toUnmodifieableDtoList(orders.getContent()),
                orders.getTotalElements(), orders.getTotalPages());
    }

    /**
     * For given order, finds a and sets products at specific revision - date and time when order
     * was created.
     *
     * @param order [{@link OrderDb}] :: the order DB entity.
     */
    private void findCorrectProductRevision(final OrderDb order) {
        final Set<UUID> productIds = order.getOrderItems().stream().map(item -> item.getProduct().getId()).collect(Collectors.toSet());

        // find revisions for products
        final List<ProductDb> auditedProducts = productAuditService.getProductsAtRevision(productIds, order.getCreatedAt());
        order.getOrderItems().forEach(item -> {
            item.setProduct(auditedProducts.stream().filter(product -> product.getId()
                    .equals(item.getProduct().getId())).findFirst().orElseThrow(() -> new EntityNotFoundException(String.format(ERROR_PRODUCT_NOT_FOUND, item.getProduct().getId()), item.getProduct().getId().toString())));
        });
    }

}
