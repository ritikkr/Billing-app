package com.ritik.Billing.service;

import com.ritik.Billing.dto.customer.CustomerRequest;
import com.ritik.Billing.dto.customer.CustomerResponse;
import com.ritik.Billing.exception.ResourceNotFoundException;
import com.ritik.Billing.model.Customer;
import com.ritik.Billing.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        // Map request DTO to entity
        customer.setCompanyName(request.getCompanyName());
        customer.setEmail(request.getEmail());
        customer.setContact(request.getContact());
        customer.setGstPreferences(request.getGstPreferences());
        customer.setAddress(request.getAddress());

        customer.setTaxId(request.getTaxId());
        customer.setActive(true); // Default to active

        Customer savedCustomer = customerRepository.save(customer);
        return mapToCustomerResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
        return mapToCustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers(boolean includeInactive) {
        List<Customer> customers = includeInactive ? customerRepository.findAll() :
                customerRepository.findAll().stream().filter(Customer::isActive).toList();
        return customers.stream()
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        // Update fields
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setContact(request.getContact());
        existingCustomer.setAddress(request.getAddress());
        existingCustomer.setCompanyName(request.getCompanyName());
        existingCustomer.setTaxId(request.getTaxId());
        existingCustomer.setGstPreferences(request.getGstPreferences());
        // createdAt and updatedAt are handled by BaseEntity

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return mapToCustomerResponse(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
        // Perform soft delete
        customer.setActive(false);
        customerRepository.save(customer);
    }

    // Helper method to map Entity to Response DTO
    private CustomerResponse mapToCustomerResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setContact(customer.getContact());
        response.setEmail(customer.getEmail());
        response.setAddress(customer.getAddress());
        response.setCompanyName(customer.getCompanyName());
        response.setTaxId(customer.getTaxId());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());
        response.setActive(customer.isActive());
        return response;
    }
}