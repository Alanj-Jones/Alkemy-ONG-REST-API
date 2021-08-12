
package com.alkemy.java.service;

import com.alkemy.java.model.User;
import com.alkemy.java.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);

		if (user == null) {
			logger.error("Error en el Login: no existe el usuario '" + email + "' en el sistema!");
			throw new UsernameNotFoundException("Username: " + email + " no existe en el sistema!");
		}

		List<GrantedAuthority> authorities = new ArrayList();
		SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + user.getRoleId().getTypeRoles().getName());
		authorities.add(role);
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				authorities);
		

	}

}
