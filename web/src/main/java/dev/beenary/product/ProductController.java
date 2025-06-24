package dev.beenary.product;

import dev.beenary.PaginationFilter;
import dev.beenary.SortingFilter;
import dev.beenary.common.utility.Defense;
import dev.beenary.common.utility.SortDirection;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Provides REST entry point for product.
 *
 * @author anapeterlic
 * @since 1.0
 */
@RestController
public class ProductController implements ProductControllerDefinition {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = Defense.notNull(productService, ProductService.class.getSimpleName());
    }

    @Override
    public ResponseEntity<CreateProductResponse> create(final CreateProductRequest request) {
        return ResponseEntity.ok(productService.create(request));
    }

    @Override
    public ResponseEntity<GetProductResponse> get(final UUID id) {
        final GetProductRequest request = new GetProductRequest();
        request.setId(id);
        return ResponseEntity.ok(productService.get(request));
    }

    @Override
    public ResponseEntity<UpdateProductResponse> update(final UUID id,
                                                        final UpdateProductRequest request) {
        request.setId(id);
        return ResponseEntity.ok(productService.update(request));
    }

    @Override
    public ResponseEntity<DeleteProductResponse> delete(final UUID id) {
        final DeleteProductRequest request = new DeleteProductRequest();
        request.setId(id);
        return ResponseEntity.ok(productService.delete(request));
    }

    @Override
    public ResponseEntity<SearchProductResponse> search(final int page, final int size, final String sortBy, final SortDirection sortDirection) {
        final SearchProductRequest request = new SearchProductRequest();
        request.setPaginationFilter(new PaginationFilter(page, size));
        request.setSortingFilter(new SortingFilter(sortBy, sortDirection));
        return ResponseEntity.ok(productService.search(request));
    }
}
