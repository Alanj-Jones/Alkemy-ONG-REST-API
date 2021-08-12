package com.alkemy.java.dto;

import com.alkemy.java.model.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {

    @NotBlank
    @ApiModelProperty(notes = "${auth.model.firstname}", required = true)
    private String firstName;
    @NotBlank
    @ApiModelProperty(notes = "${auth.model.lastname}", required = true)
    private String lastName;
    @Email
    @ApiModelProperty(notes = "${auth.model.mailregister}", required = true)
    private String email;
    @NotBlank
    @ApiModelProperty(notes = "${auth.model.passwordregister}", required = true)
    private String password;
    @NotNull
    @ApiModelProperty(notes = "${auth.model.orgid}", required = true)
    private long organizationId;

    @ApiModelProperty(notes = "${auth.model.roleid}")
    private Role roleId;
}
