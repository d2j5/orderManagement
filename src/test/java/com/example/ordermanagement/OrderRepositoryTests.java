package com.example.ordermanagement;

import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        // Create an order
        Order order = new Order("Martin Prieto", LocalDate.now(), "2806 Logan St", 100.0);

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Assert that the order is saved with a generated ID
        assertNotNull(savedOrder.getId());

        // Assert that the orderDate is set
        assertNotNull(savedOrder.getOrderDate());

        // Assert that the orderDate is the current date
        assertEquals(LocalDate.now(), savedOrder.getOrderDate());
    }

    @Test
    public void testFindOrderById() {
        // Create and persist an order
        Order order = new Order("Martin Prieto", LocalDate.now(), "2806 Logan St", 100.0);
        entityManager.persist(order);

        // Find the order by ID
        Order foundOrder = orderRepository.findById(order.getId()).orElse(null);

        // Assert that the order is found
        assertNotNull(foundOrder);

        // Assert that the customerName matches the original order
        assertEquals(order.getCustomerName(), foundOrder.getCustomerName());
    }

    @Test
    public void testUpdateOrder() {
        // Create and persist an order
        Order order = new Order("Martin Prieto", LocalDate.now(), "2806 Logan St", 100.0);
        entityManager.persist(order);

        // Modify the customerName of the order
        order.setCustomerName("Pedro Almario");

        // Update the order
        Order updatedOrder = orderRepository.save(order);

        // Assert that the customerName is updated
        assertEquals("Pedro Almario", updatedOrder.getCustomerName());
    }

    @Test
    public void testDeleteOrder() {
        // Create and persist an order
        Order order = new Order("Martin Prieto", LocalDate.now(), "2806 Logan St", 100.0);
        entityManager.persist(order);

        // Delete the order by ID
        orderRepository.deleteById(order.getId());

        // Assert that the order is deleted
        assertFalse(orderRepository.existsById(order.getId()));
    }
}
