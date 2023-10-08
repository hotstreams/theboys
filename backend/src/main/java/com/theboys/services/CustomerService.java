package com.theboys.services;

import com.theboys.data.entities.Customer;
import com.theboys.data.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Optional<Customer> getCustomer(Integer id) {
        return customerRepo.findById(id);
    }
}
