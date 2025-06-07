package com.example.crmSystem.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors, getters, and setters

    public Sale() {}

    public Sale(User user, BigDecimal amount, LocalDate saleDate) {
        this.user = user;
        this.amount = amount;
        this.saleDate = saleDate;
    }

    // Getters and Setters


}

