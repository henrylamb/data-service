package com.adp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.adp.domain.Job;

@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, Long>, CrudRepository<Job, Long> {
    void delete(Job job);
}
