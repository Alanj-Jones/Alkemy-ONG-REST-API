package com.alkemy.java.repository;

import java.util.List;
import java.util.Optional;

import com.alkemy.java.dto.OrganizationDto;
import com.alkemy.java.model.Organization;

import com.alkemy.java.model.OrganizationPublic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    List<Organization> findAllByOrderByName();

    public OrganizationDto findOrganizationById(long id);
    OrganizationDto findById(long id);
    Optional<Organization> findByName(String name);
    Organization findOrgById(Long id);
}
