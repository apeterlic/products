package dev.beenary.core.product;

import dev.beenary.api.product.create.CreateProductRequest;
import dev.beenary.api.product.create.CreateProductResponse;
import dev.beenary.api.product.delete.DeleteProductRequest;
import dev.beenary.api.product.delete.DeleteProductResponse;
import dev.beenary.api.product.read.GetProductRequest;
import dev.beenary.api.product.read.GetProductResponse;
import dev.beenary.api.product.read.SearchProductRequest;
import dev.beenary.api.product.read.SearchProductResponse;
import dev.beenary.api.product.update.UpdateProductRequest;
import dev.beenary.api.product.update.UpdateProductResponse;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.common.utility.Defense;
import dev.beenary.common.utility.SortDirection;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InternalProductService implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductRepository repository;

    private final ProductValidator validator;

    InternalProductService(final ProductRepository repository, final ProductValidator validator) {
        this.repository = Defense.notNull(repository, ProductRepository.class.getSimpleName());
        this.validator = Defense.notNull(validator, ProductValidator.class.getSimpleName());
    }

    @Override
    public GetProductResponse get(final GetProductRequest request) throws EntityNotFoundException {
        logger.debug("get() >> productId {}", request.getId());
        final ProductDb productDb = repository.getReferenceById(validator.validate(request));
        return new GetProductResponse(ProductMapper.apiMapper().toDto(productDb));
    }

    @Override
    public CreateProductResponse create(final CreateProductRequest request) {
        final ProductDb productDb = repository.save(validator.validate(request));
        logger.debug("create() << productId {}", productDb.getId());
        return new CreateProductResponse(productDb.getId());
    }

    @Override
    public UpdateProductResponse update(final UpdateProductRequest request) throws EntityNotFoundException {
        logger.debug("update() >> productId {}", request.getId());
        final ProductDb productDb = repository.save(validator.validate(request));
        return new UpdateProductResponse(ProductMapper.apiMapper().toDto(productDb));
    }

    @Override
    public DeleteProductResponse delete(final DeleteProductRequest request) throws EntityNotFoundException {
        logger.debug("delete() >> productId {}", request.getId());
        repository.softDelete(validator.validate(request));
        return new DeleteProductResponse(true);
    }

    @Override
    public SearchProductResponse search(final SearchProductRequest request) {
        final Sort sort = SortDirection.DESC.equals(request.getSortingFilter().getSortDirection())
                ? Sort.by(request.getSortingFilter().getColumn()).descending()
                : Sort.by(request.getSortingFilter().getColumn()).ascending();
        final Pageable pageable = PageRequest.of(request.getPaginationFilter().getPage() - 1,
                request.getPaginationFilter().getEntitiesPerPage(), sort);
        final Page<ProductDb> products = repository.findAllByDeletedFalse(pageable);
        return new SearchProductResponse(ProductMapper.apiMapper().toUnmodifieableDtoList(products.getContent()), products.getTotalElements(), products.getTotalPages());
    }
}
