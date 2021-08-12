package com.alkemy.java.controller;

import java.io.IOException;
import java.util.List;

import com.alkemy.java.dto.OrganizationDTOwithSlides;
import com.alkemy.java.dto.request.OrganizationDto;
import com.alkemy.java.dto.response.OrganizationResponseDto;

import com.alkemy.java.service.IOrganizationService;
import com.alkemy.java.service.impl.OrganizationServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController

@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private IOrganizationService service;
    @Autowired
    OrganizationServiceImpl serviceim;
    
    @ResponseBody
    @GetMapping("/public")
    public ResponseEntity<List<OrganizationDTOwithSlides>> showOrganizationPublic(HttpServletRequest request) {
        return ResponseEntity.ok(serviceim.findAllByOrderByName(request));
    }


    @ResponseBody
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/public")
    public ResponseEntity<OrganizationResponseDto>  postUpdateOrganization(
            @RequestBody @AuthenticationPrincipal @Valid OrganizationDto request) throws IOException {
        return ResponseEntity.ok(service.updateOrganizationDto(request));
    }

}
