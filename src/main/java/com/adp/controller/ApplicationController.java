package com.adp.controller;

import com.adp.domain.Application;
import com.adp.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

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
        Pageable applicationPage = PageRequest.of(page, items);

        Page<Application> applications = applicationService.repo.findAll(applicationPage);
        return ResponseEntity.ok(applications);
    }
    
    @PostMapping
    public ResponseEntity<Application> addApplication(@RequestBody Application application) {

        applicationService.saveApplication(application);
        return ResponseEntity.status(201).body(application);
}

}
