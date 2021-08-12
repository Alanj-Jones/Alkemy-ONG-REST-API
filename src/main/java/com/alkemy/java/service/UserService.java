package com.alkemy.java.service;

import java.io.IOException;

import com.alkemy.java.dto.UserDTOtoUpdate;
import com.alkemy.java.dto.UserDto;
import com.alkemy.java.model.User;
import javax.servlet.http.HttpServletRequest;


import com.amazonaws.services.dynamodbv2.xspec.S;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    
	 	public void deleteUserById(long id);

	    public User save (UserDto newUserDto) throws IOException;

	    public User showUser(HttpServletRequest request);

	    public Page allUsers(Pageable pageable);

    	public User partialUpdate(long id, UserDTOtoUpdate updates) throws Exception;

    	public User getById(long id);

		public User getByEmail(String email);
}

