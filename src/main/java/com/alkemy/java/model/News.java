package com.alkemy.java.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@SQLDelete(sql = "UPDATE news SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "${newsModel.id}")
    private Long id;

    @NotNull(message = "El campo name es obligatorio.")
    @Column
    @ApiModelProperty(notes = "${newsModel.name}", required = true)
    private String name;


    @NotNull(message = "El campo content es obligatorio.")
    @Column( columnDefinition="TEXT")
    @ApiModelProperty(notes = "${newsModel.content}", required = true)
    private String content;

    @NotNull(message = "El campo image es obligatorio")
    @Column
    @ApiModelProperty(notes = "${newsModel.image}" , required = true)
    private String image;

    @ManyToOne(cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name ="categoriesId")
    @ApiModelProperty(notes = "${newsModel.categories}")
    private Categories categories;

    @Column(name="deleted")
    @ApiModelProperty(notes = "${newsModel.deleted}")
    private boolean deleted;

    @CreationTimestamp
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @ApiModelProperty(notes = "${newsModel.createDate}")
    private LocalDate createDate;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @ApiModelProperty(notes = "${newsModel.updateDate}")
    private LocalDate updateDate;


}
