package com.adp.service;

import com.adp.domain.Application;
import com.adp.domain.Customer;
import com.adp.repository.ApplicationRepository;
import com.adp.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    public ApplicationRepository repo;

    public List<Application> findAllGivenJobId(Long jobId) {
        return repo.findByJobId(jobId);
    }


    public Application getApplication(long id) {
        return repo.findById(id).get();
    }

    public void saveApplication(Application application) {
        repo.save(application);
    }
}
