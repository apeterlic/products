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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductValidator validator;

    ProductServiceImpl(final ProductRepository repository, final ProductValidator validator) {
        this.repository = Defense.notNull(repository, ProductRepository.class.getSimpleName());
        this.validator = Defense.notNull(validator, ProductValidator.class.getSimpleName());
    }

    @Override
    public GetProductResponse get(final GetProductRequest request) throws EntityNotFoundException {
        final ProductDb productDb = repository.getReferenceById(validator.validate(request));
        return new GetProductResponse(ProductDb.apiMapper().toDto(productDb));
    }

    @Override
    public CreateProductResponse create(final CreateProductRequest request) {
        final ProductDb productDb = repository.save(validator.validate(request));
        return new CreateProductResponse(productDb.getId());
    }

    @Override
    public UpdateProductResponse update(final UpdateProductRequest request) throws EntityNotFoundException {
        final ProductDb productDb = repository.save(validator.validate(request));
        return new UpdateProductResponse(ProductDb.apiMapper().toDto(productDb));
    }

    @Override
    public DeleteProductResponse delete(final DeleteProductRequest request) throws EntityNotFoundException {
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
        final Page<ProductDb> products = repository.findAll(pageable);
        return new SearchProductResponse(ProductDb.apiMapper().toUnmodifieableDtoList(products.getContent()), products.getTotalElements(), products.getTotalPages());
    }
}
