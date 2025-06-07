package com.example.crmSystem.Repository;


import com.example.crmSystem.model.SalesMetrics;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesMetricsRepository extends CrudRepository<SalesMetrics, Long> {

    @Query(value = """
    SELECT 
        s.user_id AS user_id,
        SUM(s.amount) AS total_sales,
        SUM(CASE WHEN MONTH(s.sale_date) = MONTH(CURRENT_DATE()) 
                  AND YEAR(s.sale_date) = YEAR(CURRENT_DATE()) 
                 THEN s.amount ELSE 0 END) AS monthly_sales,
        SUM(CASE WHEN s.sale_date >= CURDATE() - INTERVAL 3 MONTH 
                 THEN s.amount ELSE 0 END) AS quarterly_sales
    FROM sales s
    GROUP BY s.user_id
    """, nativeQuery = true)
    List<SalesMetrics> findAllSalesMetrics();

    @Query(value = "SELECT user_id, total_sales, monthly_sales, quarterly_sales FROM sales_metrics where user_id=?", nativeQuery = true)
    SalesMetrics findByUserId(Long userId);
}

