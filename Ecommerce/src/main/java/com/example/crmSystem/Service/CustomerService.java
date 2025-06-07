package com.example.crmSystem.Service;

import com.example.crmSystem.Repository.CustomerRepository;
import com.example.crmSystem.model.Customer;
import com.example.crmSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getCustomersAssignedTo(User user) {
        return customerRepository.findByAssignedTo(user);
    }

    public List<Customer> getCustomersForUser(User user) {
        return customerRepository.findByAssignedTo(user);
    }
}
