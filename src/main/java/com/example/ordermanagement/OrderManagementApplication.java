package com.example.ordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderManagementApplication {

    public static void main(String[] args) {
        // The main class of the OrderManagementApplication.
        // The SpringApplication.run() method starts the Spring Boot application.
        // It takes two parameters: the main class (OrderManagementApplication) and the command-line arguments (args).
        SpringApplication.run(OrderManagementApplication.class, args);
    }

    // The @SpringBootApplication annotation combines three commonly used Spring annotations:
    // - @Configuration: Indicates that the class is a configuration class that contains Spring bean definitions.
    // - @EnableAutoConfiguration: Enables Spring Boot's auto-configuration feature that automatically configures the application based on dependencies and classpath.
    // - @ComponentScan: Scans for Spring components (such as controllers, services, and repositories) within the current package and its subpackages.

    // By combining these annotations, the OrderManagementApplication class becomes the entry point of the Spring Boot application.
}

