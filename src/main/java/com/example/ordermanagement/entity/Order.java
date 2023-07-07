package com.example.ordermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

/**
 * Represents an Order entity, stored in the database as a table named "order".
 */
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer name must not be blank")
    private String customerName;

    private LocalDate orderDate;

    @NotBlank(message = "Shipping address must not be blank")
    private String shippingAddress;

    @NotNull(message = "Total must not be null")
    @Positive(message = "Total must be a positive number")
    private Double total;

    /**
     * Default constructor for the Order entity.
     */
    public Order() {
        // Constructor sin argumentos
    }

    /**
     * Constructor for creating an Order entity with all the required fields.
     *
     * @param customerName    The name of the customer placing the order. @NotBlank
     * @param orderDate       The date when the order was placed, ID and date are automatically generated.
     * @param shippingAddress The shipping address for the order. @NotBlank
     * @param total           The total amount of the order. @NotNull, @Positive
     */
    public Order(String customerName, LocalDate orderDate, String shippingAddress, Double total) {
        this.customerName = customerName;
        this.orderDate = LocalDate.now(); // Set the current date
        this.shippingAddress = shippingAddress;
        this.total = total;
    }

    /**
     * Getters and setters
     */
    public Long getId() {
        return id;
    }


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public LocalDate getOrderDate() {
        return orderDate;
    }


    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }


    public String getShippingAddress() {
        return shippingAddress;
    }


    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }


    public Double getTotal() {
        return total;
    }


    public void setTotal(Double total) {
        this.total = total;
    }
}

