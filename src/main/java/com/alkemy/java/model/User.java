package com.alkemy.java.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.lang.Nullable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El campo 'firstName' no debe estar vacio.")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "El campo 'lastName' no debe estar vacio.")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "El campo 'email' no debe estar vacio.")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "El campo 'password' no debe estar vacio.")
    @Column(name = "password")
    private String password;

    @Nullable
    @Column(name = "photo")
    private String photo;

    @NotNull(message = "El campo 'roleId' no debe estar vacio.")
    @ManyToOne()
    @JoinColumn(name = "roles_id") 
    Role roleId;

    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @NotNull(message = "El Usuario debe pertenecer a una Organización existente")
    @ManyToOne()
    @JoinColumn(name = "organization_id") 
    Organization organization;

    @UpdateTimestamp
    private LocalDateTime modificationDate;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    }

