package dev.beenary.web.resource;

import dev.beenary.api.category.read.GetCategoryResponse;
import dev.beenary.api.currency.read.GetCurrencyResponse;
import dev.beenary.common.utility.Defense;
import dev.beenary.core.category.CategoryService;
import dev.beenary.core.currency.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides REST entry point for resources.
 *
 * @author anapeterlic
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/resources")
public class ResourceController implements ResourceControllerDefinition {

    private final CurrencyService currencyService;

    private final CategoryService categoryService;

    public ResourceController(final CurrencyService currencyService, final CategoryService categoryService) {
        this.currencyService = Defense.notNull(currencyService, CurrencyService.class.getSimpleName());
        this.categoryService = Defense.notNull(categoryService, CategoryService.class.getSimpleName());
    }

    @Override
    public ResponseEntity<GetCurrencyResponse> getCurrencies() {
        return ResponseEntity.ok(currencyService.findAll());
    }

    @Override
    public ResponseEntity<GetCategoryResponse> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
