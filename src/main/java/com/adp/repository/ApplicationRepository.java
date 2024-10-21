package com.adp.repository;

import com.adp.domain.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<Application, Long>, PagingAndSortingRepository<Application, Long> {

    List<Application> findByJobId(Long jobId);
}
