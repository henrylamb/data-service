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

import com.adp.domain.Customer;
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
  public ResponseEntity<?> updateJob(@PathVariable("cust-id") long id, @RequestBody Job job) {
    
    //return error when trying to update a job that doesnt exist
    Optional<Job> optionalJob = jobService.getJob(id);
    if (optionalJob.isEmpty() || job.getId() != id || !isJobValid(job)) {
      return ResponseEntity.badRequest().body("Bad Request");
    }
    jobService.saveJob(job);
    return ResponseEntity.ok(job);
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

    private boolean isJobValid(Job job) {
        return job.getListingTitle() != null && job.getDateListed() != null && job.getPassword() != null;
      }
    
}
