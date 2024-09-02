package com.springboot.microservice.jobms.shared;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Review {
    private String id;
    private String title;
    private String description;
    private double rating;
}
