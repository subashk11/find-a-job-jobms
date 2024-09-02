package com.springboot.microservice.jobms.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "jobs")
@TypeAlias("job")
public class Job {

    // Fields for Job
    @Id
    private String _id;
    private String title;
    private String description;
    private String maxSalary;
    private String minSalary;
    private String jobLocation;
    private String companyId;
}
