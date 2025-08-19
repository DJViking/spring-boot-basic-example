package com.soprasteria.example.service;

import java.util.Optional;

import com.soprasteria.example.domain.Customer;
import com.soprasteria.example.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Optional<Customer> findById(final long id) {
        return customerRepository.findById(id);
    }

}
