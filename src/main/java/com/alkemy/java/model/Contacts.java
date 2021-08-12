package com.alkemy.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
@SQLDelete(sql = "UPDATE contacts SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "El campo name es obligatorio.")
    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "El campo email es obligatorio.")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "message")
    private String message;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;
    
    @NotNull(message = "El campo organization es obligatorio.")
    @ManyToOne()
    @JoinColumn(name = "organization_id") 
    Organization organization;
    
}
