package com.adp.service;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adp.domain.Job;
import com.adp.repository.JobRepository;

@Service
public class JobService{
    @Autowired JobRepository repo;

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
