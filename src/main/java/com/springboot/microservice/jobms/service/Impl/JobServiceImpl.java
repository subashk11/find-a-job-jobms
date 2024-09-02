package com.springboot.microservice.jobms.service.Impl;

import com.mongodb.client.result.UpdateResult;

import com.springboot.microservice.jobms.dto.JobDTO;
import com.springboot.microservice.jobms.entity.Job;
import com.springboot.microservice.jobms.repository.JobRepository;
import com.springboot.microservice.jobms.service.JobService;
import com.springboot.microservice.jobms.shared.Company;
import com.springboot.microservice.jobms.shared.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;
    private MongoTemplate mongoTemplate;
    @Autowired
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    public JobServiceImpl(JobRepository jobRepository, MongoTemplate mongoTemplate){
        this.jobRepository = jobRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<List<JobDTO>> getAllJobs() {

        try{
            List<JobDTO> jobDTOList = new ArrayList<>();

            List<Job> jobList = jobRepository.findAll();

            for(Job job : jobList){
                JobDTO jobDTO = new JobDTO();
                String url = "http://companyms:8082/companies"+job.getCompanyId();
                logger.info("API CALL FOR GETTING COMPANY INFO FOR JOB : "+ job.get_id() + " COMPANY ID : " + job.getCompanyId());
                logger.info(" COMPANY MS API URL IS : "+ url);
                Company company = restTemplate.getForObject("http://companyms:8082/companies/"+job.getCompanyId(), Company.class);

                String reviewURL = "http://reviewms:8080/reviews?companyId="+job.getCompanyId();
                logger.info("API CALL FOR GETTING REVIEW INFO FOR JOB : "+ job.get_id() + " COMPANY ID : " + job.getCompanyId());
                logger.info(" REVIEW MS API URL IS : "+ reviewURL);
                ResponseEntity<List<Review>> reviewList = restTemplate.exchange("http://reviewms:8080/reviews?companyId="+job.getCompanyId(),
                        HttpMethod.GET, null ,new ParameterizedTypeReference<List<Review>>() {});
//            Company company = restTemplate.getForObject("http://localhost:8082/companies/66d35715f079b762457d123c", Company.class);
                logger.info("RESPONSE FROM REVIEW MS API CALL", reviewList);
                jobDTO.setCompany(company);
                jobDTO.set_id(job.get_id());
                jobDTO.setDescription(job.getDescription());
                jobDTO.setJobLocation(job.getJobLocation());
                jobDTO.setMaxSalary(job.getMaxSalary());
                jobDTO.setMinSalary(job.getMinSalary());
                jobDTO.setTitle(job.getTitle());
                jobDTO.setReview(reviewList.getBody());
                jobDTOList.add(jobDTO);
            }

            return Optional.of(jobDTOList);
        } catch (Exception e){
            logger.error("Error while getting Info for Jobs ", e);
        }

        return Optional.empty();
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public boolean deleteJob(String jobId) {
        jobRepository.deleteById(jobId);
        return false;
    }

    @Override
    public Optional<Job> getJob(String jobId) {
        Optional<Job> job = jobRepository.findById(jobId);
        return job;
    }

    @Override
    public boolean updateJobDetails(String jobId, Job jobInfo) {
        // Query to get job by id
        Query query = new Query(where("_id").is(jobId));
        Update update = new Update().set("title", jobInfo.getTitle())
                .set("description", jobInfo.getDescription())
                .set("jobLocation", jobInfo.getJobLocation())
                .set("minSalary", jobInfo.getMinSalary())
                .set("maxSalary", jobInfo.getMaxSalary())
                .set("companyId", jobInfo.getCompanyId());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Job.class);

        return true;
    }
}
