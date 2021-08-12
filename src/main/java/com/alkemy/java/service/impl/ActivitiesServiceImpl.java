package com.alkemy.java.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


import com.alkemy.java.dto.ActivitiesDto;
import com.alkemy.java.exception.activities.ActivitiesException;
import com.alkemy.java.mapper.ActivitiesMapper;
import com.alkemy.java.model.Activities;
import com.alkemy.java.repository.ActivitiesRepository;
import com.alkemy.java.repository.OrganizationRepository;
import com.alkemy.java.service.ActivitiesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ActivitiesServiceImpl implements ActivitiesService {

    @Autowired
    ActivitiesRepository activitiesRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
	ActivitiesMapper mapping;
    
    @Override
    public ResponseEntity<?> createActivity(ActivitiesDto activity) {

        try {
        Activities newActivity = new Activities();
        newActivity.setName(activity.getName());
        newActivity.setContent(activity.getContent());
        newActivity.setOrganization(organizationRepository.findOrgById(activity.getOrganizationId()));
        newActivity.setImage(activity.getImage());
        activitiesRepository.save(newActivity);
        return ResponseEntity.ok("Actividad " + activity.getName() + " creada con éxito");
        } catch (ConstraintViolationException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  violaitonMessage(exc));

        }

    }

    private String violaitonMessage(ConstraintViolationException exc) {
        ConstraintViolation<?> violation = exc.getConstraintViolations().iterator().next();   
        return violation.getMessageTemplate();
    }

    @Override
	public List<ActivitiesDto> getAll() {
    	activitiesRepository.findAll();
		
		 List<ActivitiesDto> ActivitiesDTO = new ArrayList<ActivitiesDto>();
	        for (Activities act : activitiesRepository.findAll()) {
	        	ActivitiesDTO.add(mapping.toDto(act));  
	        }
	        return ActivitiesDTO;
	    }
		

	@Override
	public ActivitiesDto getById(Long id) throws Exception{
		try {
	    Optional<Activities> activitieOptional = activitiesRepository.findById(id);
		Activities activitie = activitieOptional.get();
		ActivitiesDto dto = mapping.toDto(activitie);
		return dto;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    }

    @Override
    @Transactional(readOnly = true)
    public Activities findById(Long id) {
        return activitiesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void updateActivities(Long id, ActivitiesDto activities) throws ActivitiesException {
        Activities activity=this.findById(id);
        List<ActivitiesDto> list=new ArrayList<ActivitiesDto>();
        list.add(activities);
        for ( ActivitiesDto a: list){
            if (a.getName()!=null) {
                activity.setName(activities.getName());
            }
            if (a.getImage()!=null){
                activity.setImage(activities.getImage());
            }
            if (a.getContent()!=null){
                activity.setContent(activities.getContent());
            }
            if((a.getOrganizationId()!= activity.getOrganization().getId()) && (a.getOrganizationId())!=0){
                    throw new ActivitiesException();
            }
        }
        activitiesRepository.save((activity));
    }
}
