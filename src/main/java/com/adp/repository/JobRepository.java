package com.adp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.adp.domain.Job;

public interface JobRepository extends PagingAndSortingRepository<Job, Long> {
}
