package dev.beenary.product;

import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import dev.beenary.product.create.CreateProductRequest;
import dev.beenary.product.create.CreateProductResponse;
import dev.beenary.product.delete.DeleteProductRequest;
import dev.beenary.product.delete.DeleteProductResponse;
import dev.beenary.product.read.GetProductRequest;
import dev.beenary.product.read.GetProductResponse;
import dev.beenary.product.read.SearchProductRequest;
import dev.beenary.product.read.SearchProductResponse;
import dev.beenary.product.update.UpdateProductRequest;
import dev.beenary.product.update.UpdateProductResponse;
import dev.beenary.common.utility.Defense;
import dev.beenary.common.utility.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
class ProductServiceImpl implements ProductService {

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
        final ProductDb deleted = repository.softDelete(validator.validate(request));
        return new DeleteProductResponse(ProductDb.apiMapper().toDto(deleted));
    }

    @Override
    public SearchProductResponse search(final SearchProductRequest request) {
        final Sort sort = SortDirection.DESC.equals(request.getSortingFilter().getSortDirection())
                ? Sort.by(request.getSortingFilter().getColumn()).descending()
                : Sort.by(request.getSortingFilter().getColumn()).ascending();
        final Pageable pageable = PageRequest.of(request.getPaginationFilter().getPage(),
                request.getPaginationFilter().getEntitiesPerPage(), sort);
        final Page<ProductDb> products = repository.findAll(pageable);
        return new SearchProductResponse(ProductDb.apiMapper().toUnmodifieableDtoList(products.getContent()), products.getTotalElements(), products.getTotalPages());
    }
}
