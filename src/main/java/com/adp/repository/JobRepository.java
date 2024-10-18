package com.adp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.adp.domain.Job;


@Repository
public interface JobRepository extends CrudRepository<Job, Long>{
}
