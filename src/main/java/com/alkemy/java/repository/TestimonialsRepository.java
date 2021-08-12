package com.alkemy.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.java.model.Testimonials;

import java.util.Optional;

public interface TestimonialsRepository extends JpaRepository<Testimonials, Long> {
    Optional<Testimonials> findByName(String name);
}
