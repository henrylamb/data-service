package com.adp.repository;

import java.util.List;

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

    Page<Job> findByListingTitleContainingOrJobTitleContaining(String listingTitle, String jobTitle, Pageable page);
    Page<Job> findByListingTitleContainingIgnoreCaseOrJobTitleContainingIgnoreCase(String listingTitle, String jobTitle, Pageable page);
}
