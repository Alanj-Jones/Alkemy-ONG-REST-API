package com.alkemy.java.dto;

import com.alkemy.java.model.Role;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginDto {

    @Email
    @ApiModelProperty(notes = "${auth.model.username}", required = true)
    private String email;
    @NotBlank
    @ApiModelProperty(notes = "${auth.model.password}", required = true)
    private String password;

    private Role roleId;
}
