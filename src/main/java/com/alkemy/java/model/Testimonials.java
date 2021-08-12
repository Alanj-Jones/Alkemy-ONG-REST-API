package com.alkemy.java.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "testimonials")
@SQLDelete(sql = "UPDATE testimonials SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Testimonials {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "El campo name es obligatorio")
    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;
 
    @Column(name = "content")
    private String content;
    
    @Column(name="deleted")
    private boolean deleted;

    @UpdateTimestamp
    private LocalDateTime modificationDate;
    @CreationTimestamp
    private LocalDateTime creationDate;

    
    
}


