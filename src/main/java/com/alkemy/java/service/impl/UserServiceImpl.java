package com.alkemy.java.service.impl;

import java.io.IOException;
import java.util.Optional;

import com.alkemy.java.dto.UserDTOtoUpdate;
import com.alkemy.java.dto.UserDto;
import com.alkemy.java.model.TypeRoles;
import com.alkemy.java.model.User;
import com.alkemy.java.repository.OrganizationRepository;
import com.alkemy.java.repository.UserRepository;
import com.alkemy.java.security.JwtRequestFilter;
import com.alkemy.java.security.JwtTokenService;
import com.alkemy.java.service.EmailService;
import com.alkemy.java.service.RolService;
import com.alkemy.java.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    RolService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    public UserServiceImpl(){}

    public User save(UserDto newUserDto) throws IOException {

        User user = new User(newUserDto.getFirstName(), newUserDto.getLastName(), newUserDto.getEmail(),
                passwordEncoder.encode(newUserDto.getPassword()));
        user.setRoleId(service.getByRolName(TypeRoles.ADMINISTRATOR).get());
        user.setOrganization(organizationRepository.findOrgById(newUserDto.getOrganizationId()));
        try {

            userRepository.save(user);
       
        } catch (DataAccessException exc) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario " + newUserDto.getEmail() + " ya existe", exc);
        }

        emailService.sendWelcomeMail(newUserDto.getEmail(), user.getOrganization().getId());
        return user;
    }

    public User getById(long id){
        return  userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , messageSource.getMessage("user.get.failure", null, null)));
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void deleteUserById(long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el Usuario " + id, exc);
        }
    }


    public User showUser(HttpServletRequest request) {
        String token = JwtRequestFilter.getJwtFromRequest(request).get();
        String userName = jwtTokenService.getUsernameFromToken(token);
        return userRepository.findByEmail(userName);
    }

    public Page allUsers(Pageable pageable){
        return this.userRepository.findAll(pageable);
    }
    
    @Override
	public User partialUpdate(long id, UserDTOtoUpdate updates) throws Exception  {
		
		try {
			Optional<User> userOptional = userRepository.findById(id);
			User userToUpdate = userOptional.get();
			
			if(updates.getFirstName() != null) {
				userToUpdate.setFirstName(updates.getFirstName());
			}
			if(updates.getLastName() != null) {
				userToUpdate.setLastName(updates.getLastName());
			}
			if(updates.getEmail() != null) {
				userToUpdate.setEmail(updates.getEmail());
			}
			if(updates.getPassword() != null) {
				userToUpdate.setPassword(updates.getPassword());
			}
			if(updates.getRoleId() != null) {
				userToUpdate.setRoleId(updates.getRoleId());
			}
			if(updates.getPhoto() != null) {
				userToUpdate.setPhoto(updates.getPhoto());
			}
			if(updates.getOrganizationId() != null) {
				userToUpdate.setOrganization(organizationRepository.findOrgById(updates.getOrganizationId()));
			}
			return userRepository.save(userToUpdate);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}



}
    
}
