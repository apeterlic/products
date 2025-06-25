package dev.beenary.order;

import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.create.CreateOrderResponse;
import dev.beenary.api.order.read.GetOrderRequest;
import dev.beenary.api.order.read.GetOrderResponse;
import dev.beenary.api.order.read.SearchOrderRequest;
import dev.beenary.api.order.read.SearchOrderResponse;
import dev.beenary.persistence.order.OrderDb;
import dev.beenary.persistence.order.OrderRepository;
import dev.beenary.product.ProductAuditService;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.common.utility.Defense;
import dev.beenary.common.utility.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final OrderValidator validator;

    private final ProductAuditService productAudService;

    OrderServiceImpl(final OrderRepository repository, final OrderValidator validator, final ProductAuditService productAudService) {
        this.repository = Defense.notNull(repository, OrderRepository.class.getSimpleName());
        this.validator = Defense.notNull(validator, ProductAuditService.class.getSimpleName());
        this.productAudService = Defense.notNull(productAudService, ProductAuditService.class.getSimpleName());
    }

    @Override
    public GetOrderResponse get(final GetOrderRequest request) {
        final OrderDb orderDb = repository.getReferenceById(validator.validate(request));
        return new GetOrderResponse(OrderDb.apiMapper().toDto(orderDb));
    }

    @Override
    public CreateOrderResponse create(final CreateOrderRequest request) {
        final OrderDb orderDb = repository.save(validator.validate(request));
        return new CreateOrderResponse(OrderDb.apiMapper().toDto(orderDb));
    }

    @Override
    public SearchOrderResponse search(final SearchOrderRequest request) {
        final Sort sort = SortDirection.DESC.equals(request.getSortingFilter().getSortDirection())
                ? Sort.by(request.getSortingFilter().getColumn()).descending()
                : Sort.by(request.getSortingFilter().getColumn()).ascending();
        final Pageable pageable = PageRequest.of(request.getPaginationFilter().getPage() - 1,
                request.getPaginationFilter().getEntitiesPerPage(), sort);
        final Page<OrderDb> orders =
                repository.findByCreatedAt(request.getCreatedFilter().getFrom(), pageable);

        orders.stream().forEach(order -> order.getOrderItems().forEach(item -> {
            final ProductDb auditedProduct = productAudService.getProductAtRevision(item.getProduct().getId(), order.getCreatedAt());
            item.setProduct(auditedProduct);
        }));

        return new SearchOrderResponse(OrderDb.apiMapper().toUnmodifieableDtoList(orders.getContent()),
                orders.getTotalElements(), orders.getTotalPages());
    }

}
