package com.adp.service;

import com.adp.domain.Application;
import com.adp.domain.Customer;
import com.adp.repository.ApplicationRepository;
import com.adp.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    @Autowired
    ApplicationRepository repo;

    public Iterable<Application> getAll(){
        return repo.findAll();
    }


    public Application getApplication(long id) {
        return repo.findById(id).get();
    }

    public void saveApplication(Application application) {
        repo.save(application);
    }
}
