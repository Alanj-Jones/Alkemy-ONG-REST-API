package com.alkemy.java.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "activities")
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El campo name no puede estar vacío")
    @Column(name = "name")
    private String name;

    @NotNull(message = "El campo content no puede estar vacío")
    @Column(name="content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne()
    @NotNull(message = "La acividad tiene que pertenecer a una organización")
    @JoinColumn(name = "organization_id")
    Organization organization;

    @Column(name="image")
    private String image;

    @Column(name = "deleted")
    private boolean deleted;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime UpdateDate;

}
