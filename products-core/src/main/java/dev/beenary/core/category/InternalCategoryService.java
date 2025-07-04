package dev.beenary.core.category;

import dev.beenary.api.category.read.GetCategoryResponse;
import dev.beenary.common.utility.Defense;
import dev.beenary.persistence.category.CategoryRepository;
import org.springframework.stereotype.Service;


@Service
public class InternalCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public InternalCategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = Defense.notNull(categoryRepository, CategoryRepository.class.getSimpleName());
    }

    @Override
    public GetCategoryResponse findAll() {
        return new GetCategoryResponse(CategoryMapper.apiMapper().toUnmodifieableDtoList(categoryRepository.findAll()));
    }

}
