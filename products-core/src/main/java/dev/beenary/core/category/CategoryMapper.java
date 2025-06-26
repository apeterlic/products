package dev.beenary.core.category;

import dev.beenary.api.Resource;
import dev.beenary.core.utility.ApiMapper;
import dev.beenary.persistence.category.CategoryDb;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

public final class CategoryMapper {

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private CategoryMapper() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }

    public static ApiMapper<CategoryDb, Resource> apiMapper() {
        return entity -> {
            final Resource product = new Resource();
            product.setName(entity.getValue());
            return product;
        };
    }

}
