package com.adp.service;

import java.net.URI;
import java.util.Optional;

import com.adp.domain.Job;
import com.adp.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adp.domain.Customer;
import com.adp.repository.CustomerRepository;

@Service
public class JobService{
    @Autowired CustomerRepository repo;

    @Autowired
    JobRepository jobRepository;

    public Page<Job> getJobs(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return jobRepository.findAll(pageable);
    }

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
