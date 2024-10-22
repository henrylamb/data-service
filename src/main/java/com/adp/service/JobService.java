package com.adp.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adp.domain.Application;
import com.adp.domain.Job;
import com.adp.domain.JobTransferRequest;
import com.adp.repository.JobRepository;

@Service
public class JobService{
    @Autowired
    JobRepository repo;

    public List<Application> getApplicationsOfGivenJobId(Long jobId){
        Optional<Job> job = repo.findById(jobId);
        List<Application> returnList =  new ArrayList<>();
        if(job.isPresent()){
            returnList = job.get().getApplications();
        }
        return returnList;
    }

    public Page<Job> getPaginatedJobs(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return repo.findAll(pageable);
    }

    public Iterable<Job> getAll(){
        return repo.findAll();
    }

    public Optional<Job> getJob(long id) {
        return repo.findById(id);
    }

    public URI saveJob(Job job) {
        // TODO: validate job object
        job = repo.save(job);
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(job.getId())
                .toUri();
    }

    public void delete(Job job){
        repo.delete(job);
    }

    // TODO do we have 2 validations here??
    public void transferJobToNewHiringManager(JobTransferRequest request) {
        Optional<Job> jobOptional = getJob(request.fromUserId);  // Assuming getJob returns an Optional<Job>
    
        if (!jobOptional.isPresent()) {  // Check if the job is present
            System.out.println("Job not found for that user");  // Fixed the print statement
            return;  // Exit the method if the job isn't found
        }
    
        // Proceed with transferring the job if it exists
        Job job = jobOptional.get();
        // Implement logic to transfer the job to the new hiring manager (toUserId)

        job.setUserId((long)request.toUserId);
        repo.save(job);
    }
        

    }

