package com.alkemy.java.mapper;

import org.springframework.stereotype.Component;

import com.alkemy.java.dto.ActivitiesDto;
import com.alkemy.java.model.Activities;

@Component
public class ActivitiesMapper {

	public ActivitiesDto toDto(Activities activities) {
		
		ActivitiesDto actDTO = new ActivitiesDto();
		actDTO.setContent(activities.getContent());
		actDTO.setImage(activities.getImage());
		actDTO.setName(activities.getName());
		
		return actDTO;
	}
}
