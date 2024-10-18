package com.adp.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adp.domain.Job;
import com.adp.service.JobService;

@RestController
@RequestMapping("/")
public class JobController {

    @Autowired
    JobService jobService;

    @GetMapping
    public Iterable<Job> getAll() {
        return jobService.getAll();
    }

    @GetMapping("/{cust-id}")
    public Optional<Job> getJob(@PathVariable("cust-id") long id) {
        return jobService.getCustomer(id);
    }

    @PostMapping
    public ResponseEntity<?> addJob(@RequestBody Job newJob) {
        if (!isCustomerValid(newJob)) {
            return ResponseEntity.badRequest().build();
        }

        URI location = jobService.saveJob(newJob);

        return ResponseEntity.created(location).body(newJob);
    }

    @PutMapping("/{cust-id}")
    public ResponseEntity<?> putJob(@PathVariable("cust-id") long id, @RequestBody Customer customer) {

        //return error when trying to update a customer that doesnt exist
        Optional<Customer> optionalCustomer = customerService.getCustomer(id);
        if (optionalCustomer.isEmpty() || customer.getId() != id || !isCustomerValid(customer)) {
            return ResponseEntity.badRequest().body("Bad Request");
        }
        customerService.saveCustomer(customer);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{cust-id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("cust-id") long id) {
        Optional<Customer> customer = customerService.getCustomer(id);
        if (customer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        customerService.delete(customer.get());
        return ResponseEntity.notFound().build();
    }

    private boolean isCustomerValid(Customer customer) {
        return customer.getName() != null && customer.getEmail() != null && customer.getPassword() != null;
    }
}
