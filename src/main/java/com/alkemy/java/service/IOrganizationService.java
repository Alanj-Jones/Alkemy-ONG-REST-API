package com.alkemy.java.service;

import com.alkemy.java.dto.OrganizationDTOwithSlides;
import com.alkemy.java.dto.request.OrganizationDto;
import com.alkemy.java.dto.response.OrganizationResponseDto;
import com.alkemy.java.model.Organization;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IOrganizationService {
    List<OrganizationDTOwithSlides> findAllByOrderByName(HttpServletRequest request);
    OrganizationResponseDto getOrgById(Long organizationId);
    Organization getOrganizationById(Long organizationId);
    void updateOrg(Organization org);
    OrganizationResponseDto updateOrganizationDto(OrganizationDto dto);

}
