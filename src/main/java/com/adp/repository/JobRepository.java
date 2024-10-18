package com.adp.repository;

import org.springframework.data.repository.CrudRepository;

import com.adp.domain.Job;

public interface JobRepository extends CrudRepository<Job, Long>{
}
