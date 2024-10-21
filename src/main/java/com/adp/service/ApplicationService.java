package com.adp.service;

import com.adp.domain.Application;
import com.adp.repository.ApplicationRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;


@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository repo;

    public Optional<Application> getApplication(long id) {
        return repo.findById(id);
    }

    public Page<Application> findAll(int page, int size) {
        Pageable applicationPage = PageRequest.of(page, size);

        return repo.findAll(applicationPage);
    }

    public void saveApplication(Application application) {
        repo.save(application);
    }
}
