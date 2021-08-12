package com.alkemy.java.repository;

import com.alkemy.java.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

    public User findByEmail (String username);

}
