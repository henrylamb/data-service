package com.adp.repository;

import org.springframework.data.repository.CrudRepository;
import com.adp.domain.Job;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<Job, Long>{
}
