package com.ritik.Billing.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") // Allow all origins for simplicity; adjust as needed
public class BaseController {
    // This is a base controller that can be extended by other controllers.
    // It can contain common methods or configurations for all controllers in the application.
    // Currently, it does not have any specific functionality.
    @GetMapping("/")
    public String home() {
        return "Welcome to the Billing System API!";
    }
}
