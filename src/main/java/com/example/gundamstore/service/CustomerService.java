package com.example.gundamstore.service;

import com.example.gundamstore.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private List<Customer> customers = new ArrayList<>();

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public Customer getCustomerById(String id) {
        Optional<Customer> customer = customers.stream().filter(c -> c.getId().equals(id)).findFirst();
        return customer.orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    public Customer updateCustomer(String id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        if (customer != null) {
            customer.setName(customerDetails.getName());
            customer.setEmail(customerDetails.getEmail());
            customer.setPhoneNumber(customerDetails.getPhoneNumber());
            customer.setAddress(customerDetails.getAddress());
            return customer;
        }
        return null;
    }

    public boolean deleteCustomer(String id) {
        return customers.removeIf(customer -> customer.getId().equals(id));
    }
}
