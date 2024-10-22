package com.adp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.adp.domain.Job;

@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, Long>, CrudRepository<Job, Long> {
    void delete(Job job);

    @Query(value = "SELECT * FROM JOB WHERE listing_title = ?1 or job_title = ?1 or listing_status = ?1")
    Page<Job> findByListingTitleOrJobTitleOrListingStatus(String searchString, Pageable page);
}
