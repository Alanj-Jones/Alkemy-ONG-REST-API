package com.alkemy.java.dto;

import java.util.List;

import com.alkemy.java.model.Slide;

import lombok.Data;

@Data
public class OrganizationDTOwithSlides {

	
	String name;
	String image;
	String phone;
	String adress;
	String facebookUrl;
	String linkedinUrl;
	String instagramUrl;
	List<Slide> slidesOrg;
	
	
}
