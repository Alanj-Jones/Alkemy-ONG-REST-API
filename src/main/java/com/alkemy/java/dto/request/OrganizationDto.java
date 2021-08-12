package com.alkemy.java.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;

@Builder
@AllArgsConstructor
@Data
public class OrganizationDto {
    @NotNull(message = "El campo name es obligatorio")
    private String name;

    @NotNull(message = "El campo image es obligatoria")
    private String image;

    @Size(min = 5, max = 50)
    private String address;

    @Size(min = 6, max = 20)
    private Long phone;

    @Email(message = "Email debe ser un email válido")
    private String email;

    @NotNull(message = "El campo welcomeText es obligatorio")
    private String welcomeText;

    @Size(max = 300)
    private String aboutUsText;
}
