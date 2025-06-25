package dev.beenary.product;

import dev.beenary.DataGenerator;
import dev.beenary.api.product.Product;
import dev.beenary.api.product.create.CreateProductRequest;
import dev.beenary.api.product.create.CreateProductResponse;
import dev.beenary.api.product.delete.DeleteProductRequest;
import dev.beenary.api.product.delete.DeleteProductResponse;
import dev.beenary.api.product.read.GetProductRequest;
import dev.beenary.api.product.read.GetProductResponse;
import dev.beenary.api.product.read.SearchProductResponse;
import dev.beenary.api.product.update.UpdateProductRequest;
import dev.beenary.api.product.update.UpdateProductResponse;
import dev.beenary.common.exception.BusinessException;
import dev.beenary.common.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static dev.beenary.DataGenerator.toJson;
import static dev.beenary.product.ProductValidator.ERROR_DUPLICATE_CODE;
import static dev.beenary.product.ProductValidator.ERROR_PRODUCT_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
@ImportAutoConfiguration
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void givenCreateProductRequest_whenCreate_thenReturnOk() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");
        request.getPayload().setCode("ABC");

        final UUID id = UUID.randomUUID();
        when(productService.create(request)).thenReturn(new CreateProductResponse(id));

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void givenCreateProductRequestNoCode_whenCreate_thenReturnError() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");
        request.getPayload().setCode(null);

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print()).andExpect(jsonPath("$.errors[0].message").value("Field 'code' must be provided.")).andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenCreateProductRequestDuplicateCode_whenCreate_thenReturnError() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");

        final String exceptionMessage = String.format(ERROR_DUPLICATE_CODE, request.getPayload().getCode());
        when(productService.create(request)).thenThrow(new BusinessException(exceptionMessage));

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print()).andExpect(jsonPath("$.errors[0].message").value(exceptionMessage)).andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenCreateProductRequestNoName_whenCreate_thenReturnError() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");
        request.getPayload().setName(null);

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print()).andExpect(jsonPath("$.errors[0].message").value("Field 'name' must be provided.")).andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenCreateProductRequestNoDescription_whenCreate_thenReturnError() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");
        request.getPayload().setDescription(null);

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print()).andExpect(jsonPath("$.errors[0].message").value("Field 'description' must be " + "provided.")).andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenCreateProductRequestNoPrice_whenCreate_thenReturnError() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");
        request.getPayload().setPrice(null);

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print()).andExpect(jsonPath("$.errors[0].message").value("Field 'price' must be provided.")).andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenCreateProductRequestInvalidPrice_whenCreate_thenReturnError() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");
        request.getPayload().setPrice(BigDecimal.ZERO);

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print()).andExpect(jsonPath("$.errors[0].message").value("Value of the field 'price' should be greater than 0.00.")).andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenCreateProductRequestNoCategory_whenCreate_thenReturnError() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");
        request.getPayload().setCategory(null);

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print()).andExpect(jsonPath("$.errors[0].message").value("Field 'category' must be " + "provided.")).andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenCreateProductRequestNoVat_whenCreate_thenReturnError() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");
        request.getPayload().setVat(null);

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print()).andExpect(jsonPath("$.errors[0].message").value("Field 'vat' must be provided.")).andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenCreateProductRequestNoCurrency_whenCreate_thenReturnError() throws Exception {
        final CreateProductRequest request = DataGenerator.fromJson(CreateProductRequest.class, "product-request.json");
        request.getPayload().setCurrency(null);

        mockMvc.perform(post("/v1/products").content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print()).andExpect(jsonPath("$.errors[0].message").value("Field 'currency' must be " + "provided.")).andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenGetProductRequest_whenGet_thenReturnOk() throws Exception {
        final GetProductRequest request = new GetProductRequest();
        request.setId(UUID.randomUUID());

        when(productService.get(request)).thenReturn(new GetProductResponse(new Product()));

        mockMvc.perform(get("/v1/products/{id}", request.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void givenGetProductRequestInvalidId_whenGet_thenReturnError() throws Exception {
        final GetProductRequest request = new GetProductRequest();
        request.setId(UUID.randomUUID());

        when(productService.get(request)).thenThrow(new EntityNotFoundException(String.format(ERROR_PRODUCT_NOT_FOUND, request.getId())));

        mockMvc.perform(get("/v1/products/{id}", request.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }


    @Test
    void givenUpdateProductRequest_whenUpdate_thenReturnOk() throws Exception {
        final UpdateProductRequest request = DataGenerator.fromJson(UpdateProductRequest.class, "product-request.json");

        final UUID id = UUID.randomUUID();
        when(productService.update(request)).thenReturn(new UpdateProductResponse(new Product()));

        mockMvc.perform(put("/v1/products/{id}", id).content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void givenUpdateProductRequestInvalidId_whenUpdate_thenReturnError() throws Exception {
        final UpdateProductRequest request = DataGenerator.fromJson(UpdateProductRequest.class, "product-request.json");

        request.getPayload().setId(UUID.randomUUID());
        when(productService.update(request)).thenThrow(new EntityNotFoundException(String.format(ERROR_PRODUCT_NOT_FOUND, request.getId())));

        mockMvc.perform(put("/v1/products/{id}", request.getId()).content(toJson(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void givenDeleteProductRequest_whenUpdate_thenReturnOk() throws Exception {
        final DeleteProductRequest request = new DeleteProductRequest();
        request.setId(UUID.randomUUID());

        when(productService.delete(request)).thenReturn(new DeleteProductResponse(true));

        mockMvc.perform(delete("/v1/products/{id}", request.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void givenDeleteProductRequestInvalidId_whenUpdate_thenReturnError() throws Exception {
        final DeleteProductRequest request = new DeleteProductRequest();
        request.setId(UUID.randomUUID());

        when(productService.delete(request)).thenThrow(new EntityNotFoundException(String.format(ERROR_PRODUCT_NOT_FOUND, request.getId())));

        mockMvc.perform(delete("/v1/products/{id}", request.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void search() throws Exception {
        when(productService.search(any())).thenReturn(new SearchProductResponse(new ArrayList<>(), 1, 1));

        mockMvc.perform(get("/v1/products").param("page", "1").param("size", "10").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.totalPages").value(1)).andExpect(jsonPath("$.totalElements").value(1));
    }
}