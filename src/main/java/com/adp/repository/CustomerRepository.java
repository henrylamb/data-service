package com.adp.repository;

import org.springframework.data.repository.CrudRepository;

import com.adp.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
}
