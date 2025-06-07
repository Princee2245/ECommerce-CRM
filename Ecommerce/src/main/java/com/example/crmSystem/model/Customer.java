package com.example.crmSystem.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String contactDetails;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors, getters, and setters

    public Customer() {}

    public Customer(String name, String contactDetails, String notes, User assignedTo) {
        this.name = name;
        this.contactDetails = contactDetails;
        this.notes = notes;
        this.assignedTo = assignedTo;
    }

    // Getters and Setters


}

