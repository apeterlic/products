package dev.beenary.web.order;

import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.create.CreateOrderResponse;
import dev.beenary.api.order.read.OrderDetail;
import dev.beenary.core.order.OrderService;
import dev.beenary.web.DataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static dev.beenary.web.DataGenerator.toJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ImportAutoConfiguration
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void givenCreateOrderRequest_whenCreate_thenReturnOk() throws Exception {
        final CreateOrderRequest request = DataGenerator.fromJson(CreateOrderRequest.class,
                "order-request.json");

        when(orderService.create(request)).thenReturn(new CreateOrderResponse(new OrderDetail()));

        mockMvc.perform(post("/v1/orders")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").exists());
    }

    @Test
    void givenCreateOrderRequestWithoutProductId_whenCreate_thenReturnOk() throws Exception {
        final CreateOrderRequest request = DataGenerator.fromJson(CreateOrderRequest.class,
                "order-request.json");
        request.getPayload().getOrderItems().getFirst().setProductId(null);

        when(orderService.create(request)).thenReturn(new CreateOrderResponse(new OrderDetail()));

        mockMvc.perform(post("/v1/orders")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void givenCreateOrderRequestWithoutQuantity_whenCreate_thenReturnOk() throws Exception {
        final CreateOrderRequest request = DataGenerator.fromJson(CreateOrderRequest.class,
                "order-request.json");
        request.getPayload().getOrderItems().getFirst().setQuantity(null);

        when(orderService.create(request)).thenReturn(new CreateOrderResponse(new OrderDetail()));

        mockMvc.perform(post("/v1/orders")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void get() {
    }

    @Test
    void create() {
    }

    @Test
    void search() {
    }
}