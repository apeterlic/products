package dev.beenary.web.order;

import dev.beenary.api.PaginationFilter;
import dev.beenary.api.SortingFilter;
import dev.beenary.api.TimeFilter;
import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.create.CreateOrderResponse;
import dev.beenary.api.order.read.GetOrderRequest;
import dev.beenary.api.order.read.GetOrderResponse;
import dev.beenary.api.order.read.OrderDetail;
import dev.beenary.api.order.read.SearchOrderRequest;
import dev.beenary.api.order.read.SearchOrderResponse;
import dev.beenary.common.utility.SortDirection;
import dev.beenary.core.order.OrderService;
import dev.beenary.web.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static dev.beenary.web.TestDataHelper.toJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        final CreateOrderRequest request = TestDataHelper.fromJsonFile(CreateOrderRequest.class,
                "order-request.json");

        when(orderService.create(request)).thenReturn(new CreateOrderResponse(new OrderDetail()));

        mockMvc.perform(post("/v1/orders")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").exists());
    }

    @Test
    void givenCreateOrderRequestWithoutProductId_whenCreate_thenReturnBadRequest() throws Exception {
        final CreateOrderRequest request = TestDataHelper.fromJsonFile(CreateOrderRequest.class,
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
    void givenCreateOrderRequestWithoutQuantity_whenCreate_thenReturnBadRequest() throws Exception {
        final CreateOrderRequest request = TestDataHelper.fromJsonFile(CreateOrderRequest.class,
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
    void givenId_whenGet_thenReturnOk() throws Exception {
        final GetOrderRequest request = new GetOrderRequest();
        request.setId(UUID.randomUUID());

        when(orderService.get(request)).thenReturn(new GetOrderResponse(new OrderDetail()));

        mockMvc.perform(get("/v1/orders/{id}", request.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenSearchOrderRequest_whenSearch_thenReturnOk() throws Exception {
        final LocalDateTime from = LocalDateTime.now().minusDays(1);
        final LocalDateTime to = LocalDateTime.now();
        final SearchOrderRequest request = new SearchOrderRequest();
        request.setPaginationFilter(new PaginationFilter(1, 10));
        request.setSortingFilter(new SortingFilter("id", SortDirection.ASC));
        request.setCreatedFilter(new TimeFilter(from, to));

        when(orderService.search(request)).thenReturn(new SearchOrderResponse(List.of(new OrderDetail()), 1, 1));

        mockMvc.perform(get("/v1/orders")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("sortDirection", "ASC")
                        .param("from", from.toString())
                        .param("to", to.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void givenSearchOrderRequestToBeforeFrom_whenSearch_thenReturnBadRequest() throws Exception {
        final LocalDateTime from = LocalDateTime.now();
        final LocalDateTime to = LocalDateTime.now().minusDays(2);

        mockMvc.perform(get("/v1/orders")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("sortDirection", "ASC")
                        .param("from", from.toString())
                        .param("to", to.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0].message").value("Field 'to' must greater than the value provided in the field 'from'."));
    }
}