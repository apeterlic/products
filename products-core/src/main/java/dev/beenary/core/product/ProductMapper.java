package dev.beenary.core.product;

import dev.beenary.api.product.Product;
import dev.beenary.core.utility.ApiMapper;
import dev.beenary.core.utility.EntityMapper;
import dev.beenary.persistence.product.ProductDb;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

public final class ProductMapper {

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private ProductMapper() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }

    public static ApiMapper<ProductDb, Product> apiMapper() {
        return entity -> {
            final Product product = new Product();
            product.setId(entity.getId());
            product.setCode(entity.getCode());
            product.setName(entity.getName());
            product.setDescription(entity.getDescription());
            product.setPrice(entity.getPrice());
            product.setVat(entity.getVat());
            product.setCurrency(entity.getCurrency());
            product.setCategory(entity.getCategory());
            product.setStockQuantity(entity.getStockQuantity());
            product.setEnabled(entity.isEnabled());
            product.setCreatedAt(entity.getCreatedAt());
            product.setUpdatedAt(entity.getUpdatedAt());
            return product;
        };
    }

    public static EntityMapper<Product, ProductDb> entityMapper() {
        return dto -> {
            final ProductDb product = new ProductDb();
            product.setId(dto.getId());
            product.setCode(dto.getCode());
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setVat(dto.getVat());
            product.setCurrency(dto.getCurrency());
            product.setCategory(dto.getCategory());
            product.setStockQuantity(dto.getStockQuantity());
            product.setEnabled(dto.getEnabled());
            return product;
        };
    }

}
