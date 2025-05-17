package com.bitespeed.contact_consolidation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data                       //for generating all getters and setters (as lombok is used)
@NoArgsConstructor          //creates a no-argument constructor
@AllArgsConstructor         //creates a constructor with all fields
@Entity                     //it maps this class as table Contact in db
public class Contact {

    @Id                     //marks primary key for the table Contact
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autogenerates value for id
    private long id;

    private String phoneNumber;
    private String email;
    private int linkedId;
    private LinkPrecedence linkPrecedence;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;

}
