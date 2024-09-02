package com.springboot.microservice.jobms.service;



import com.springboot.microservice.jobms.dto.JobDTO;
import com.springboot.microservice.jobms.entity.Job;

import java.util.List;
import java.util.Optional;


public interface JobService {

    Optional<List<JobDTO>> getAllJobs();
    void createJob(Job job);
    boolean deleteJob(String id);
    Optional<Job> getJob(String id);
    boolean updateJobDetails(String id, Job job);

}
