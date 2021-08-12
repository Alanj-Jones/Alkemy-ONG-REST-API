package com.alkemy.java.service;

import com.alkemy.java.model.Role;
import com.alkemy.java.model.TypeRoles;
import com.alkemy.java.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {

    @Autowired
    RolesRepository rolRepository;

    public Optional<Role> getByRolName(TypeRoles typeRoles){
        return rolRepository.findByTypeRoles(typeRoles);
    }

    public void save(Role roles){
        rolRepository.save(roles);
    }

}
