package com.adp.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.adp.domain.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adp.domain.Job;
import com.adp.domain.JobTransferRequest;
import com.adp.service.JobService;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    JobService jobService;

    //url: ../api/job?page=page&items=items
    @GetMapping(value="/", params={"page","items"})
    public Page<Job> getPaginatedJobs(@RequestParam int page, @RequestParam (defaultValue = "20") int items){
        return jobService.getPaginatedJobs(page, items);
    }

    //url: ../api/job/{id}/applications
    @GetMapping("/{id}/applications")
    public ResponseEntity<?> getApplications(@PathVariable(value="id") Long id){
        List<Application> applications = jobService.getApplicationsOfGivenJobId(id);
        if (applications.isEmpty()) {
            return ResponseEntity.status(404).body("No applications found for the given job ID");
        } else {
            return ResponseEntity.ok(applications);
        }
    }

    @GetMapping
    public Iterable<Job> getAll() {
        return jobService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Job> getJob(@PathVariable("id") long id) {
        return jobService.getJob(id);
    }

    // TODO /job/{id}/filter={filter}
    // TODO GET /job/page?={page}&items?={items}

    @PostMapping
    public ResponseEntity<?> addJob(@RequestBody Job newJob) {
        if (!isJobValid(newJob)) {
            return ResponseEntity.badRequest().build();
        }

        URI location = jobService.saveJob(newJob);

        return ResponseEntity.created(location).body(newJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable("id") long id, @RequestBody Job job) {
      
      //return error when trying to update a job that doesnt exist
      Optional<Job> optionalJob = jobService.getJob(id);
      if (optionalJob.isEmpty() || job.getId() != id || !isJobValid(job)) {
        return ResponseEntity.badRequest().build();
      }
      jobService.saveJob(job);
      return ResponseEntity.ok(job); // This returns the job in the response body
    }

    @PutMapping("/transfer")
    public ResponseEntity<?> transferJobToNewHiringManager(@RequestBody JobTransferRequest request) {

        boolean success = jobService.transferJobToNewHiringManager(request); // Was the operation to update successful?

        if (!success) {
            return ResponseEntity.notFound().build();
        }

        Optional<Job> updatedJob = jobService.getJob(request.getJobId());

        if (updatedJob.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(updatedJob.get()); // return the updated job with new hiring manager in the response body 
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
        Optional<Job> job = jobService.getJob(id);
        if (job.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        jobService.delete(job.get());
        return ResponseEntity.noContent().build();
    }

    private boolean isJobValid(Job job) {
        return job.getId() != null && job.getJobTitle() != null && job.getJobDescription() != null;
    }
}
