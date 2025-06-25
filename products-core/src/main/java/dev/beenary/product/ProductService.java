package dev.beenary.product;

import dev.beenary.common.exception.EntityNotFoundException;
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


/**
 * Represents a service class for managing operations on products.
 *
 * @author anapeterlic
 */
public interface ProductService {

    GetProductResponse get(final GetProductRequest request) throws EntityNotFoundException;

    CreateProductResponse create(final CreateProductRequest request);

    UpdateProductResponse update(final UpdateProductRequest request) throws EntityNotFoundException;

    DeleteProductResponse delete(final DeleteProductRequest request) throws EntityNotFoundException;

    SearchProductResponse search(final SearchProductRequest request);

}
