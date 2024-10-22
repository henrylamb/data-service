package com.adp.service;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.adp.repository.JobRepository;
import com.adp.domain.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adp.domain.Job;

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
}
