package dev.beenary.product;

import dev.beenary.common.exception.EntityNotFoundException;
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
