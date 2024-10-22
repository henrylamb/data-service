package com.adp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adp.domain.Application;
import com.adp.domain.Job;
import com.adp.request.ApplicationRequest;
import com.adp.service.ApplicationService;
import com.adp.service.JobService;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    JobService jobService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable("id") long id) {
        Optional<Application> optApplication = applicationService.getApplication(id);
        if(optApplication.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optApplication.get());
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<Application> getApplicationStatistics(@PathVariable("id") long id) {
        Optional<Application> optApplication = applicationService.getApplication(id);
        if(optApplication.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        Application application = optApplication.get();
        application.statisticsOnly();


        return ResponseEntity.ok(application);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable("id") long id) {
        // delete application
        applicationService.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> putApplication(@PathVariable("id") long id, @RequestBody ApplicationRequest applicationReq) {
        System.out.println(applicationReq);
        Optional<Job> jobOptional = jobService.getJob(applicationReq.getJobId());

        Application application = applicationReq.convertToApplication();
        application.setId(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            application.setJob(job);
        } else {
            // Handle the case where the job is not found
            // For example, you might want to throw an exception or return an error response
            return ResponseEntity.badRequest().build();
        }

        if (!isApplicationValid(application)) {
            return ResponseEntity.badRequest().build();
        }

        applicationService.updateApplication(application);

        return ResponseEntity.ok(application);
    }

    @PutMapping("/manager/{id}")
    public ResponseEntity<Application> putApplicationManager(@PathVariable("id") long id, @RequestBody ApplicationRequest applicationReq) {
        Optional<Job> jobOptional = jobService.getJob(applicationReq.getJobId());

        Optional<Application> optApplication = applicationService.getApplication(id);
        if(optApplication.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Application application = optApplication.get();

        application.setApplicationStatus(applicationReq.getApplicationStatus());

        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            application.setJob(job);
        } else {
            // Handle the case where the job is not found
            // For example, you might want to throw an exception or return an error response
            return ResponseEntity.badRequest().build();
        }

        if (!isApplicationValid(application)) {
            return ResponseEntity.badRequest().build();
        }

        applicationService.updateApplication(application);

        return ResponseEntity.ok(application);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Application>> getApplications(@RequestParam("page") int page, @RequestParam("items") int items) {

        Page<Application> applications = applicationService.findAll(page, items);
        return ResponseEntity.ok(applications);
    }
    
    @PostMapping
    public ResponseEntity<Application> addApplication(@RequestBody ApplicationRequest applicationReq) {
        System.out.println(applicationReq);
      Optional<Job> jobOptional = jobService.getJob(applicationReq.getJobId());




        Application application = applicationReq.convertToApplication();

        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            application.setJob(job);
        } else {
            // Handle the case where the job is not found
            // For example, you might want to throw an exception or return an error response
            return ResponseEntity.badRequest().build();
        }

         if (!isApplicationValid(application)) {
            return ResponseEntity.badRequest().build();
         }

        applicationService.saveApplication(application);
        return ResponseEntity.status(201).body(application);
}


private boolean isApplicationValid(Application application) {
    // return application.getJobId() != null && application.getCandidateEmail() != null 
    // && application.getCandidateId() != null;
    if (application.getCandidateEmail() != null && application.getCandidateId() != null) {
        return true;
    }

    return false;
}

}
