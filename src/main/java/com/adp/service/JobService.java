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
        if (job.isPresent()){
            returnList = job.get().getApplications();
        }
        return returnList;
    }

    public Page<Job> getPaginatedJobs(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        //added by Kate
        Page<Job> jobs = repo.findAll(pageable);
        System.out.println("Jobs found:" + jobs.getContent().size());
        return jobs;
        //return repo.findAll(pageable);
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

  
    public boolean transferJobToNewHiringManager(JobTransferRequest request) {

        Optional<Job> optionalJob = getJob(request.getJobId()); // Get teh job we want to change

        if (optionalJob.isEmpty()) { // If the job does not exist, return false
            return false;
        }

        Job job = optionalJob.get(); // Get it (if it does exist) 

        job.setUserId(request.toUserId); // Re-assign userId
        saveJob(job); // save job to database
        return true; // return true 
    }
        

    }

