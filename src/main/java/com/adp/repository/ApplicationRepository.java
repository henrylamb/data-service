package com.adp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.adp.Application;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long>{

}
