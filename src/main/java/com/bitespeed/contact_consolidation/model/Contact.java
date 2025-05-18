package com.bitespeed.contact_consolidation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data                       //for generating all getters and setters (as lombok is used)
@NoArgsConstructor          //creates a no-argument constructor
@AllArgsConstructor         //creates a constructor with all fields
@Entity                     //it maps this class as table Contact in db
@Table(name = "contact")
public class Contact {

    @Id                     //marks primary key for the table Contact
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autogenerates value for id
    private long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Column(name = "linked_id")
    private long linkedId;

    @Column(name = "link_precedence")
    private LinkPrecedence linkPrecedence;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

}
