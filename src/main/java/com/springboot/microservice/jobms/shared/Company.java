package com.springboot.microservice.jobms.shared;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Company {


    private String id;
    private String name;
    private String description;
}
