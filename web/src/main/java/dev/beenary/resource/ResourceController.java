package dev.beenary.resource;

import dev.beenary.category.CategoryService;
import dev.beenary.category.GetCategoryResponse;
import dev.beenary.currency.CurrencyService;
import dev.beenary.currency.GetCurrencyResponse;
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
        this.currencyService = currencyService;
        this.categoryService = categoryService;
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
