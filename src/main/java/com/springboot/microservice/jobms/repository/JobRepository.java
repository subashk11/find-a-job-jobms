package com.springboot.microservice.jobms.repository;


import com.springboot.microservice.jobms.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JobRepository extends MongoRepository<Job, String> {
}
