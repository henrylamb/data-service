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
import com.adp.service.JobService;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    JobService jobService;

    //url: ../api//job/page?=page&items?=items
    @GetMapping(value="/", params={"page","items"})
    public Page<Job> getJobs(@RequestParam int page, @RequestParam (defaultValue = "20") int items){
        return jobService.getJobs(page, items);
    }

    //url: ../api/job/{id}/applications
    @GetMapping("/{id}/applications")
    public List<Application> getApplications(@PathVariable long id){
        //TODO check if it is better to have job that has the link to the applications
        return null;
    }

    @GetMapping
    public Iterable<Job> getAll() {
        return jobService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Job> getJob(@PathVariable long id) {
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
            return ResponseEntity.badRequest().body("Bad Request");
        }
        jobService.saveJob(job);
        return ResponseEntity.ok(job);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id) {
        Optional<Job> job = jobService.getJob(id);
        if (job.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        jobService.delete(job.get());
        return ResponseEntity.notFound().build();
    }

    private boolean isJobValid(Job job) {
        return job.getId() != null && job.getJobTitle() != null && job.getJobDescription() != null;
    }
}
