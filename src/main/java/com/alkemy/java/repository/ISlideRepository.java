package com.alkemy.java.repository;

import com.alkemy.java.model.Slide;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISlideRepository extends JpaRepository<Slide, Long> {

    @Query(value = "SELECT max(slideOrder) FROM Slide")
    public int max();
    
    @Query(
			value = "SELECT * FROM slides WHERE organization_id = ? ORDER BY slide_order ASC ",
			nativeQuery = true
			)
    public List<Slide> findAllByOrgId(@Param("organization_id")Long id);
    
    
}
