package dev.beenary.product;

import dev.beenary.common.exception.BusinessException;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.common.utility.Defense;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import dev.beenary.api.product.create.CreateProductRequest;
import dev.beenary.api.product.delete.DeleteProductRequest;
import dev.beenary.api.product.read.GetProductRequest;
import dev.beenary.api.product.update.UpdateProductRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Validation class for validating product requests.
 *
 * @author anapeterlic
 * @since 1.0
 */
@Component
public class ProductValidator {

    public static final String ERROR_PRODUCT_NOT_FOUND = "Product with id %s not found";
    public static final String ERROR_DUPLICATE_CODE = "Product with code %s already exists";
    public static final String ERROR_PRODUCT_CODE_NOT_FOUND = "Product with code %s not found";
    private final ProductRepository repository;

    public ProductValidator(final ProductRepository repository) {
        this.repository = Defense.notNull(repository, ProductRepository.class.getSimpleName());
    }

    public UUID validate(final GetProductRequest request) {
        validateExistence(request.getId());
        return request.getId();
    }

    public ProductDb validate(final CreateProductRequest request) {
        // TODO check currency and category
        validateCodeExistence(request.getPayload().getCode());
        return ProductDb.entityMapper().toEntity(request.getPayload());
    }

    public UUID validate(final DeleteProductRequest request) {
        validateExistence(request.getId());
        return request.getId();
    }

    public ProductDb validate(final UpdateProductRequest request) {
        // TODO check currency and category
        validateExistence(request.getId());
        validateUniqueCode(request.getPayload().getCode());
        return ProductDb.entityMapper().toEntity(request.getPayload());
    }

    private void validateExistence(final UUID id) {
        final Optional<ProductDb> product = repository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException(String.format(ERROR_PRODUCT_NOT_FOUND, id));
        }
    }

    private void validateUniqueCode(final String code) {
        final Optional<ProductDb> product = repository.findByCodeAndEnabledTrueAndDeletedFalse(code);
        if (product.isEmpty()) {
            throw new BusinessException(String.format(ERROR_PRODUCT_CODE_NOT_FOUND, code));
        }
    }

    private void validateCodeExistence(final String code) {
        final Optional<ProductDb> product = repository.findByCodeAndEnabledTrueAndDeletedFalse(code);
        if (product.isPresent()) {
            throw new BusinessException(String.format(ERROR_DUPLICATE_CODE, code));
        }
    }
}
