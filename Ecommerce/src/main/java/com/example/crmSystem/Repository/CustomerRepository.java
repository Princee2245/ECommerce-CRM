package com.example.crmSystem.Repository;


import com.example.crmSystem.model.Customer;
import com.example.crmSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByAssignedTo(User id);
}


