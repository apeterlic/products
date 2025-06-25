package dev.beenary.core.product;

import dev.beenary.api.product.create.CreateProductRequest;
import dev.beenary.api.product.delete.DeleteProductRequest;
import dev.beenary.api.product.read.GetProductRequest;
import dev.beenary.api.product.update.UpdateProductRequest;
import dev.beenary.common.exception.BusinessException;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.common.utility.Defense;
import dev.beenary.persistence.category.CategoryRepository;
import dev.beenary.persistence.currency.CurrencyRepository;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
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

    public static final String ERROR_PRODUCT_NOT_FOUND = "Product with id %s not found.";
    public static final String ERROR_CATEGORY_NOT_FOUND = "Category %s not found.";
    public static final String ERROR_CURRENCY_NOT_FOUND = "Currency %s not found.";
    public static final String ERROR_DUPLICATE_CODE = "Product with code %s already exists.";
    public static final String ERROR_PRODUCT_CODE_NOT_FOUND = "Product with code %s not found.";

    private final ProductRepository repository;
    private final CurrencyRepository currencyRepository;
    private final CategoryRepository categoryRepository;

    public ProductValidator(final ProductRepository repository, final CurrencyRepository currencyRepository, final CategoryRepository categoryRepository) {
        this.repository = Defense.notNull(repository, ProductRepository.class.getSimpleName());
        this.currencyRepository = Defense.notNull(currencyRepository, CurrencyRepository.class.getSimpleName());
        this.categoryRepository = Defense.notNull(categoryRepository, CategoryRepository.class.getSimpleName());
    }

    public UUID validate(final GetProductRequest request) {
        validateExistence(request.getId());
        return request.getId();
    }

    public ProductDb validate(final CreateProductRequest request) {
        validateCurrency(request.getPayload().getCurrency());
        validateCategory(request.getPayload().getCategory());
        validateCodeExistence(request.getPayload().getCode());
        return ProductDb.entityMapper().toEntity(request.getPayload());
    }

    public UUID validate(final DeleteProductRequest request) {
        validateExistence(request.getId());
        return request.getId();
    }

    public ProductDb validate(final UpdateProductRequest request) {
        request.getPayload().setId(request.getId());

        validateCurrency(request.getPayload().getCurrency());
        validateCategory(request.getPayload().getCategory());
        validateExistence(request.getId());
        validateUniqueCode(request.getPayload().getCode());
        return ProductDb.entityMapper().toEntity(request.getPayload());
    }

    private void validateCategory(final String category) {
        final boolean exists = categoryRepository.existsByValue(category);
        if (!exists) {
            throw new EntityNotFoundException(String.format(ERROR_CATEGORY_NOT_FOUND, category), category);
        }
    }

    private void validateCurrency(final String currency) {
        final boolean exists = currencyRepository.existsByValue(currency);
        if (!exists) {
            throw new EntityNotFoundException(String.format(ERROR_CURRENCY_NOT_FOUND, currency), currency);
        }
    }

    private void validateExistence(final UUID id) {
        final Optional<ProductDb> product = repository.findByIdAndDeletedFalse(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException(String.format(ERROR_PRODUCT_NOT_FOUND, id), id.toString());
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
