package com.adp.service;

import java.net.URI;
import java.util.Optional;

import com.adp.repository.JobRepository;
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


    public Page<Job> getJobs(int pageNumber, int size) {
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
