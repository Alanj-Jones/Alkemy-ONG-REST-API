package com.alkemy.java.service.impl;

import com.alkemy.java.dto.OrganizationDTOwithSlides;
import com.alkemy.java.dto.request.OrganizationDto;
import com.alkemy.java.dto.response.OrganizationResponseDto;
import com.alkemy.java.exception.organization.NullOrganizationException;
import com.alkemy.java.exception.organization.OrganizationException;
import com.alkemy.java.mapper.OrganizationMapper;
import com.alkemy.java.model.Organization;
import com.alkemy.java.model.Slide;
import com.alkemy.java.model.User;
import com.alkemy.java.repository.OrganizationRepository;
import com.alkemy.java.repository.UserRepository;
import com.alkemy.java.security.JwtRequestFilter;
import com.alkemy.java.security.JwtTokenService;
import com.alkemy.java.service.IOrganizationService;
import com.alkemy.java.service.ISlideService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements IOrganizationService {

	@Autowired
	private final OrganizationRepository repository;

	private final OrganizationMapper organizationMapper;
	@Autowired
	JwtTokenService jwtTokenService;

	@Autowired
	ISlideService serviceSlide;

	@Autowired
	UserRepository userRepository;


	public List<OrganizationDTOwithSlides> findAllByOrderByName(HttpServletRequest request) {
		String token = JwtRequestFilter.getJwtFromRequest(request).get();
		String userName = jwtTokenService.getUsernameFromToken(token);
		User user= userRepository.findByEmail(userName);

		List<OrganizationDTOwithSlides> orgSlidesList = new ArrayList<OrganizationDTOwithSlides>();
		List<Organization> org = repository.findAll();
		Organization organization = user.getOrganization();
		Long idOrg = organization.getId();
		

			
			OrganizationDTOwithSlides orgSlides = new OrganizationDTOwithSlides();
			orgSlides = organizationMapper.OrganizatinToDTO(organization, orgSlides);
			idOrg = organization.getId();

				try {
					List<Slide> slides = serviceSlide.findByIdOrg(idOrg);
					orgSlides.setSlidesOrg(slides);
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			orgSlidesList.add(orgSlides);



		return orgSlidesList;
	}




	public OrganizationResponseDto getOrgById(Long organizationId) {
		return organizationMapper.toOrganizationDto(searchOrganizationById(organizationId), "");
	}

	public Organization getOrganizationById(Long organizationId) {
		return searchOrganizationById(organizationId);
	}

	public void updateOrg(Organization org) {
		repository.save(org);
	}

	public OrganizationResponseDto updateOrganizationDto(OrganizationDto dto) {
		Organization orgUpdated = organizationMapper.updateOrganization(dto, searchOrganizationByName(dto.getName()));
		updateOrg(orgUpdated);
		return organizationMapper.toOrganizationDto(orgUpdated, "La Organization ha sido modificado correctamente");
	}

	private Organization searchOrganizationByName(String name) {
		Optional<Organization> org = repository.findByName(name);
		if (!org.isPresent()) {
			OrganizationException.notFoundEntityException(new NullOrganizationException());
			return null;
		}
		return org.get();
	}

	private Organization searchOrganizationById(Long organizationId) {
		Optional<Organization> org = repository.findById(organizationId);
		if (!org.isPresent()) {
			OrganizationException.notFoundEntityException(new NullOrganizationException());
			return null;
		}
		return org.get();
	}
}
