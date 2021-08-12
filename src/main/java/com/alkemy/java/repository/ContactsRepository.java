package com.alkemy.java.repository;

import java.util.List;

import com.alkemy.java.model.Contacts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Long>{

    List<Contacts> findAllByOrderByName();
    
}
