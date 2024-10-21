package com.adp.service;

import com.adp.domain.Application;
import com.adp.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApplicationService {
    @Autowired
    public ApplicationRepository repo;

    public Application getApplication(long id) {
        return repo.findById(id).get();
    }

    public void saveApplication(Application application) {
        repo.save(application);
    }
}
