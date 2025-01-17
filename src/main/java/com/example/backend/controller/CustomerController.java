package com.example.backend.controller;

import com.example.backend.entity.Booking;
import com.example.backend.entity.Customer;
import com.example.backend.entity.Movie;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    public Iterable<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @GetMapping("/{customerId}")
    public List<Booking> getOrdersByCustomer(@PathVariable Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return bookingRepository.findByCustomer(customer.get());
    }
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(createdCustomer);
    }


}
