package com.adp.service;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adp.domain.Customer;
import com.adp.repository.CustomerRepository;

@Service
public class JobService{
    @Autowired CustomerRepository repo;

    public Iterable<Customer> getAll(){
        return repo.findAll();
    }

    public Optional<Customer> getCustomer(long id) {
        return repo.findById(id);
    }

    public URI saveCustomer(Customer customer) {
        customer = repo.save(customer);
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUri();
    }

    public void delete(Customer customer){
        repo.delete(customer);
    }
}
