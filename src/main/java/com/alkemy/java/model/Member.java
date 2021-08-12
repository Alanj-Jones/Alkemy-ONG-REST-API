package com.alkemy.java.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class Member {

    public static final int MIN_NAME_SIZE = 4;
    public static final int MAX_NAME_SIZE = 30;
    public static final int MIN_DESCRIPTION_SIZE = 4;
    public static final int MAX_DESCRIPTION_SIZE = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "${membersModel.id}", required = true)
    private Long id;

    @Column(nullable = false, length = MAX_NAME_SIZE)
    @NotNull(message = "El campo name es obligatorio")
    @ApiModelProperty(notes = "${membersModel.name}", required = true)
    private String name;

    @Column()
    @URL
    @ApiModelProperty(notes = "${membersModel.facebookUrl}", required = true)
    private String facebookUrl;

    @Column()
    @URL
    @ApiModelProperty(notes = "${membersModel.instagramUrl}", required = true)
    private String instagramUrl;

    @Column()
    @URL
    @ApiModelProperty(notes = "${membersModel.linkedinUrl}", required = true)
    private String linkedinUrl;

    @Column(nullable = false)
    @NotNull(message = "El campo image es obligatorio")
    @ApiModelProperty(notes = "${membersModel.image}", required = true)
    private String image;

    @Column(length = MAX_DESCRIPTION_SIZE)
    @ApiModelProperty(notes = "${membersModel.description}", required = true)
    private String description;

    @CreationTimestamp
    @Column()
    @ApiModelProperty(notes = "${membersModel.regdate}", required = true)
    private Timestamp regdate;

    @UpdateTimestamp
    @Column()
    @ApiModelProperty(notes = "${membersModel.updatedate}", required = true)
    private Timestamp updatedate;

    @Column()
    @ApiModelProperty(notes = "${membersModel.deleted}", required = true)
    private boolean deleted;

    @NotNull(message = "El campo organization es obligatorio.")
    @ManyToOne()
    @JoinColumn(name = "organization_id")
    @ApiModelProperty(notes = "${membersModel.organization}", required = true)
    Organization organization;

}