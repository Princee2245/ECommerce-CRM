package com.example.crmSystem.Service;


import com.example.crmSystem.Repository.SaleRepository;
import com.example.crmSystem.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Sale findById(Long id) {
        return saleRepository.findById(id).orElse(null);
    }

    public List<Sale> findByUserId(Long userId) {
        return saleRepository.findByUserId(userId);
    }

    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }

    public List<Sale> findByUserEmail(String email) {
        return saleRepository.findByUserEmail(email);
    }
}
