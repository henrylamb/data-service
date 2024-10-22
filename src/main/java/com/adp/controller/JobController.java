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

    // url: ../api/job?page=page&items=items
    @GetMapping(value = "/page")
    public ResponseEntity<Page<Job>> getPaginatedJobs(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int items) {
        return ResponseEntity.ok(jobService.getPaginatedJobs(page, items));
    }

    // url: ../api/job/{id}/applications
    @GetMapping("/{id}/applications")
    public ResponseEntity<?> getApplications(@PathVariable(value = "id") Long id) {
        List<Application> applications = jobService.getApplicationsOfGivenJobId(id);
        if (applications.isEmpty()) {
            return ResponseEntity.status(404).body("No applications found for the given job ID");
        } else {
            return ResponseEntity.ok(applications);
        }
    }

    // url: ../api/job/search?value=value&page=page&items=items all users
    @GetMapping(value = "/search")
    public ResponseEntity<?> getSearchResult(@RequestParam String value, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int items) {
        Page<Job> jobs = jobService.getPagesFromSearch(value, page, items);

        if (jobs.getTotalElements() > 0) {
            return ResponseEntity.ok(jobs);
        }
        return ResponseEntity.ok("Error"); //TODO revisit
    }

    @GetMapping
    public Iterable<Job> getAll() {
        return jobService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Job> getJob(@PathVariable("id") long id) {
        return jobService.getJob(id);
    }

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

        // return error when trying to update a job that doesnt exist
        Optional<Job> optionalJob = jobService.getJob(id);
        if (optionalJob.isEmpty() || job.getId() != id || !isJobValid(job)) {
            return ResponseEntity.badRequest().build();
        }
        jobService.saveJob(job);
        return ResponseEntity.ok(job); // This returns the job in the response body
    }

    @PutMapping("/transfer")
    public ResponseEntity<?> transferJobToNewHiringManager(@RequestBody JobTransferRequest request) {

        Optional<Job> optionalJob = jobService.getJob(request.getJobId());

        if (optionalJob.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (jobService.transferJobToNewHiringManager(optionalJob.get().getUserId(), request)) {
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.badRequest().build();
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
