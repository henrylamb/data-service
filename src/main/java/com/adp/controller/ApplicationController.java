package com.adp.controller;

import com.adp.domain.Application;
import com.adp.service.ApplicationService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@RequestParam("id") long id) {
        Optional<Application> optApplication = applicationService.getApplication(id);
        if(optApplication.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optApplication.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable("id") long id) {
        // delete application
        applicationService.getApplication(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> putApplication(@PathVariable("id") long id, @RequestBody Application application) {
        // update application from a users perpective
        applicationService.saveApplication(application);

        return ResponseEntity.ok(application);
    }

    @PutMapping("/manager/{id}")
    public ResponseEntity<Application> putApplicationManager(@PathVariable("id") long id, @RequestBody Application application) {
        // update application by a hiring manager
        applicationService.saveApplication(application);

        return ResponseEntity.ok(application);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Application>> getApplications(@RequestParam("page") int page, @RequestParam("items") int items) {

        Page<Application> applications = applicationService.findAll(page, items);
        return ResponseEntity.ok(applications);
    }
    
    @PostMapping
    public ResponseEntity<Application> addApplication(@RequestBody Application application) {
     if (!isApplicationValid(application)) {
        return ResponseEntity.badRequest().build();
     }

        applicationService.saveApplication(application);
        return ResponseEntity.status(201).body(application);
}


private boolean isApplicationValid(Application application) {
    // return application.getJobId() != null && application.getCandidateEmail() != null 
    // && application.getCandidateId() != null;
    return false;
}

}
