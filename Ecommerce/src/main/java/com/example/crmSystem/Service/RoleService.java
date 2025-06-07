package com.example.crmSystem.Service;

import com.example.crmSystem.Repository.RoleRepository;
import com.example.crmSystem.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

    public void save(Role role){
        roleRepository.save(role);
    }

    public Role findById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
