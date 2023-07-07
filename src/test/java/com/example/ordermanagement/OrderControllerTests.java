package com.example.ordermanagement;

import com.example.ordermanagement.controller.OrderController;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createOrder_ValidInput_ReturnsCreatedStatus() throws Exception {
        // Arrange: Create a new order and configure the mock repository to return the saved order
        Order order = new Order("John Doe", LocalDate.now(), "123 Main St", 100.0);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act & Assert: Perform a POST request to create an order and expect a created status (201)
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerName\": \"John Doe\", \"shippingAddress\": \"123 Main St\", \"total\": 100.0 }"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void getAllOrders_ReturnsListOfOrders() throws Exception {
        // Arrange: Create a list of orders and configure the mock repository to return the list
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("Marcos Prieto", LocalDate.now(), "123 Main St", 100.0));
        orders.add(new Order("Marta Slim", LocalDate.now(), "456 Elm St", 200.0));
        when(orderRepository.findAll()).thenReturn(orders);

        // Act & Assert: Perform a GET request to retrieve all orders and expect an OK status (200)
        // Additionally, assert that the response JSON contains a property named "length" with a value of 2
        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    public void getOrderById_ExistingOrderId_ReturnsOrder() throws Exception {
        // Arrange: Create an order and configure the mock repository to return the order by its ID
        Order order = new Order("Marcos Prieto", LocalDate.now(), "123 Main St", 100.0);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act & Assert: Perform a GET request to retrieve an order by its ID and expect an OK status (200)
        // Additionally, assert that the response JSON contains the expected values for each property
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("Marcos Prieto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shippingAddress").value("123 Main St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(100.0));
    }

    @Test
    public void getOrderById_NonexistentOrderId_ReturnsNotFoundStatus() throws Exception {
        // Arrange: Configure the mock repository to return an empty Optional for a non-existent order ID
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Perform a GET request to retrieve an order by a non-existent ID and expect a Not Found status (404)
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateOrder_ValidInput_ReturnsUpdatedOrder() throws Exception {
        // Arrange: Create an order and configure the mock repository to return the order by its ID
        Order order = new Order("Marcos Prieto", LocalDate.now(), "123 Main St", 100.0);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act & Assert: Perform a PUT request to update an order and expect an OK status (200)
        // Additionally, assert that the response JSON contains the updated values for each property
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerName\": \"Updated Name\", \"shippingAddress\": \"456 Elm St\", \"total\": 200.0 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("Updated Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shippingAddress").value("456 Elm St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(200.0));
    }

    @Test
    public void createOrder_EmptyFields_ReturnsInternalServerError() throws Exception {
        // Act & Assert: Perform a POST request to create an order with empty fields and expect an Internal Server Error status (500)
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerName\": \"\", \"shippingAddress\": \"\", \"total\": null }"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void updateOrder_NonexistentOrderId_ReturnsNotFoundStatus() throws Exception {
        // Arrange: Configure the mock repository to return an empty Optional for a non-existent order ID
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Perform a PUT request to update a non-existent order and expect a Not Found status (404)
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerName\": \"Updated Name\", \"shippingAddress\": \"456 Elm St\", \"total\": 200.0 }"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteOrder_ExistingOrderId_ReturnsOkStatus() throws Exception {
        // Arrange: Configure the mock repository to return true for the existence of an order with the specified ID
        when(orderRepository.existsById(1L)).thenReturn(true);

        // Act & Assert: Perform a DELETE request to delete an order and expect an OK status (200)
        // Additionally, assert that the response content is "Order deleted successfully"
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));
    }

    @Test
    public void deleteOrder_NonexistentOrderId_ReturnsNotFoundStatus() throws Exception {
        // Arrange: Configure the mock repository to return false for the existence of an order with the specified ID
        when(orderRepository.existsById(1L)).thenReturn(false);

        // Act & Assert: Perform a DELETE request to delete a non-existent order and expect a Not Found status (404)
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
