package dev.beenary.core.category;

import dev.beenary.api.category.GetCategoryResponse;

/**
 * Represents a service class for managing operations on category table.
 *
 * @author anapeterlic
 */
public interface CategoryService {

    GetCategoryResponse findAll();
}
