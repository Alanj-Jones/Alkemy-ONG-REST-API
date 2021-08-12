package com.alkemy.java.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;

import com.alkemy.java.model.Role;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTOtoUpdate implements Serializable {

	private static final long serialVersionUID = 7462943197385678736L;

	    private String firstName;
	   
	    private String lastName;
	    @Email
	    private String email;
	    
	    private String password;
	    
	    private Role roleId;
	    
	    private String photo;

	    private Long organizationId;
}