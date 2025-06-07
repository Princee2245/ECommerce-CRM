package com.example.crmSystem.Service;




import com.example.crmSystem.Repository.SalesMetricsRepository;
import com.example.crmSystem.model.SalesMetrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesMetricsService {

    private final SalesMetricsRepository salesMetricsRepository;

    @Autowired
    public SalesMetricsService(SalesMetricsRepository salesMetricsRepository) {
        this.salesMetricsRepository = salesMetricsRepository;
    }

    public List<SalesMetrics> getAllSalesMetrics() {
        return salesMetricsRepository.findAllSalesMetrics();
    }

    public SalesMetrics findByUserId(long id){
       return salesMetricsRepository.findByUserId(id);
    }
}
