package dev.beenary.core.category;

import dev.beenary.api.category.GetCategoryResponse;
import dev.beenary.common.utility.Defense;
import dev.beenary.persistence.category.CategoryDb;
import dev.beenary.persistence.category.CategoryRepository;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(final CategoryRepository categoryRepository) {
        this.categoryRepository = Defense.notNull(categoryRepository, CategoryRepository.class.getSimpleName());
    }

    @Override
    public GetCategoryResponse findAll() {
        return new GetCategoryResponse(CategoryDb.apiMapper().toUnmodifieableDtoList(categoryRepository.findAll()));
    }

}
