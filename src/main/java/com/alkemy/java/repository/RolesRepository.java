package com.alkemy.java.repository;

import com.alkemy.java.model.Role;
import com.alkemy.java.model.TypeRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByTypeRoles(TypeRoles typeRoles);
}
