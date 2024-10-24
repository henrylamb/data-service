package com.adp.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.adp.domain.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.adp.domain.Job;
import com.adp.domain.JobTransferRequest;
import com.adp.dto.ManagerStatistics;
import com.adp.service.JobService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    JobService jobService;

    @PreAuthorize("hasRole('ROLE_HIRING-MANAGER')")
    @GetMapping("/manager/{managerId}/stats")
    public ResponseEntity<?> getJobsByHiringManager(
        @PathVariable Long managerId
    ) {
        List<Job> jobs = jobService.getJobsByUserId(managerId);
        int totalJobs = jobs.size();
        int openJobs = 0;
        int closedJobs = 0;
        int totalApplications = 0;
        int pendingApplications = 0;
        int reviewedApplications = 0;
        int acceptedApplications = 0;
        int rejectedApplications = 0;

        for (Job job : jobs) {
            if ("open".equalsIgnoreCase(job.getListingStatus())) {
                openJobs++;
            } else if ("closed".equalsIgnoreCase(job.getListingStatus())) {
                closedJobs++;
            }

            List<Application> applications = job.getApplications();
            totalApplications += applications.size();
            for (Application application : applications) {
                switch (application.getApplicationStatus().toLowerCase()) {
                    case "pending":
                        pendingApplications++;
                        break;
                    case "reviewed":
                        reviewedApplications++;
                        break;
                    case "accepted":
                        acceptedApplications++;
                        break;
                    case "rejected":
                        rejectedApplications++;
                        break;
                }
            }
        }

        ManagerStatistics stats = new ManagerStatistics(
            totalJobs,
            openJobs,
            closedJobs,
            totalApplications,
            pendingApplications,
            reviewedApplications,
            acceptedApplications,
            rejectedApplications,
            pendingApplications, // pendingReviews is the same as pendingApplications
            openJobs // openPositions is the same as openJobs
        );

        return ResponseEntity.ok(stats);
    }


    // url: ../api/job?page=page&items=items
    // @PreAuthorize("permitAll()")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<Job>> getPaginatedJobs(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "items", defaultValue = "20") int items) {
        return ResponseEntity.ok(jobService.getPaginatedJobs(page, items));  
    }

    @PreAuthorize("hasRole('ROLE_HIRING-MANAGER')")
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<Page<Job>> getJobsByHiringManager(
        @PathVariable Long managerId,
        @RequestParam int page,
        @RequestParam int items
    ) {
        Page<Job> jobs = jobService.getJobByUserId(managerId, page, items);
        return ResponseEntity.ok(jobs);
    }

    // url: ../api/job/{id}/applications
    @PreAuthorize("hasRole('ROLE_HIRING-MANAGER')")
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
    @PreAuthorize("permitAll()")
    @GetMapping(value = "/search")
    public ResponseEntity<?> getSearchResult(
            @RequestParam(name = "value") String value,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "items", defaultValue = "20") int items) {

        Page<Job> jobs = jobService.getPagesFromSearch(value, page, items);

        if (jobs.getTotalElements() > 0) {
            return ResponseEntity.ok(jobs);
        }
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public Iterable<Job> getAll() {
        return jobService.getAll();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public Optional<Job> getJob(@PathVariable("id") long id) {
        return jobService.getJob(id);
    }

    @PreAuthorize("hasRole('ROLE_HIRING-MANAGER')")
    @PostMapping
    public ResponseEntity<?> addJob(@RequestBody Job newJob) {
        if (newJob.getJobTitle() == null && newJob.getJobDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        URI location = jobService.saveJob(newJob);

        return ResponseEntity.created(location).body(newJob);
    }

    @PreAuthorize("hasRole('ROLE_HIRING-MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable("id") long id, @RequestBody Job job) {

        // return error when trying to update a job that doesnt exist
        Optional<Job> optionalJob = jobService.getJob(id);
        if (optionalJob.isEmpty() || job.getId() != id || !isJobValid(job)) {
            return ResponseEntity.badRequest().build();
        }
        job.setApplications(optionalJob.get().getApplications());
        jobService.saveJob(job);
        return ResponseEntity.ok(job); // This returns the job in the response body
    }
  

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    
    @PreAuthorize("hasRole('ROLE_HIRING-MANAGER')")
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
