package com.alkemy.java.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@Entity
@ApiModel(description = "Detalles sobre categorías.")
public class Categories {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value ="${categories.api.property.id}", example = "1")
    private long id;

    @Column(name = "name")
    @NotNull(message = "{categories.name.not.null}")
    @NotBlank(message = "{categories.name.not.blank}")
    @ApiModelProperty(value = "${categories.api.property.name}", example = "Eventos.")
    private String name;

    @Column(name = "description")
    @NotNull(message = "{categories.description.not.null}")
    @ApiModelProperty(value = "${categories.api.property.description}", example = "Categoria designada para contenido de eventos.")
    private String description;

    @Column(name = "image")
    @NotNull(message = "{categories.image.not.null}")
    @ApiModelProperty(value = "${categories.api.property.image}", example = "https://homepages.cae.wisc.edu/~ece533/images/monarch.png")
    private String image;

    @Column(name = "deleted")
    @ApiModelProperty(value = "${categories.api.property.deleted}")
    private Boolean deleted;

    @CreationTimestamp
    @ApiModelProperty(value = "${categories.api.property.creation.date}")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @ApiModelProperty(value = "${categories.api.property.update.date}")
    private LocalDateTime updateDateTime;

}
