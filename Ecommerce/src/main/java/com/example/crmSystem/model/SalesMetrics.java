package com.example.crmSystem.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@SqlResultSetMapping(
        name = "SalesMetricsMapping",
        entities = {
                @EntityResult(entityClass = SalesMetrics.class, fields = {
                        @FieldResult(name = "userId", column = "user_id"),
                        @FieldResult(name = "totalSales", column = "total_sales"),
                        @FieldResult(name = "monthlySales", column = "monthly_sales"),
                        @FieldResult(name = "quarterlySales", column = "quarterly_sales")
                })
        }
)

@Entity
public class SalesMetrics {

    @Id
    private Long userId;
    private BigDecimal totalSales;
    private BigDecimal monthlySales;
    private BigDecimal quarterlySales;

    // Constructors, getters, and setters

    public SalesMetrics() {}

    public SalesMetrics(Long userId, BigDecimal totalSales, BigDecimal monthlySales, BigDecimal quarterlySales) {
        this.userId = userId;
        this.totalSales = totalSales;
        this.monthlySales = monthlySales;
        this.quarterlySales = quarterlySales;
    }

    // Getters and Setters


}
