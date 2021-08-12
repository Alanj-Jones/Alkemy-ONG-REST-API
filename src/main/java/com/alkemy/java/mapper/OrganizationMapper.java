package com.alkemy.java.mapper;

import com.alkemy.java.dto.OrganizationDTOwithSlides;
import com.alkemy.java.dto.request.OrganizationDto;
import com.alkemy.java.dto.response.OrganizationResponseDto;
import com.alkemy.java.model.Organization;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrganizationMapper {

    public OrganizationResponseDto toOrganizationDto(Organization organization, String message) {
        OrganizationResponseDto dto = OrganizationResponseDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .phone(organization.getPhone())
                .address(organization.getAddress())
                .email(organization.getEmail())
                .welcomeText(organization.getWelcomeText())
                .image(organization.getImage())
                .aboutUsText(organization.getAboutUsText())
                .creationDateTime(organization.getCreateDateTime().toString())
                .updateDateTime(organization.getUpdateDateTime().toString())
                .message(message)
                .build();
        return dto;
    }

    public Organization updateOrganization(OrganizationDto dto, Organization org) {
        LocalDateTime updateDateTime = LocalDateTime.now();
        Organization.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .address(dto.getAddress())
                .phone(String.valueOf(dto.getPhone()))
                .email(dto.getEmail())
                .welcomeText(dto.getWelcomeText())
                .aboutUsText(dto.getAboutUsText())
                .updateDateTime(updateDateTime)
                .deleted(false)
                .build();
        return org;
    }

    
      public OrganizationDTOwithSlides  OrganizatinToDTO(Organization organization, OrganizationDTOwithSlides orgDTO) {
    	  
    	  
    	  orgDTO.setAdress(organization.getAddress());
    	  orgDTO.setImage(organization.getImage());
		  orgDTO.setName(organization.getName());
		  orgDTO.setPhone(organization.getPhone());
		  orgDTO.setInstagramUrl(organization.getInstagramUrl());
		  orgDTO.setFacebookUrl(organization.getFacebookUrl());
		  orgDTO.setLinkedinUrl(organization.getLinkedinUrl());

			
		  return orgDTO;
    	  
      }
}
