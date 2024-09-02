package com.springboot.microservice.jobms.dto;

import com.springboot.microservice.jobms.shared.Company;
import com.springboot.microservice.jobms.shared.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {

    private String _id;
    private String title;
    private String description;
    private String maxSalary;
    private String minSalary;
    private String jobLocation;
    private String companyId;
    private Company company;
    private List<Review> review;
}
