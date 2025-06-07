package com.example.crmSystem.model;


import jakarta.persistence.*;

import java.math.BigDecimal;

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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(BigDecimal monthlySales) {
        this.monthlySales = monthlySales;
    }

    public BigDecimal getQuarterlySales() {
        return quarterlySales;
    }

    public void setQuarterlySales(BigDecimal quarterlySales) {
        this.quarterlySales = quarterlySales;
    }
}
