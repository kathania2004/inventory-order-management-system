package com.himanshu.inventory.service;

import com.himanshu.inventory.dto.*;
import com.himanshu.inventory.entity.Customer;
import com.himanshu.inventory.exception.BusinessException;
import com.himanshu.inventory.exception.ResourceNotFoundException;
import com.himanshu.inventory.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        if (repository.existsByEmailIgnoreCase(request.email())) {
            throw new BusinessException("Customer email already exists: " + request.email());
        }
        Customer c = new Customer();
        c.setName(request.name());
        c.setEmail(request.email());
        c.setPhone(request.phone());
        return toResponse(repository.save(c));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Customer getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(c.getId(), c.getName(), c.getEmail(), c.getPhone());
    }
}
