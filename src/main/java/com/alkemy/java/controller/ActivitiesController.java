package com.alkemy.java.controller;


import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.alkemy.java.dto.ActivitiesDto;
import com.alkemy.java.service.ActivitiesService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class ActivitiesController {

	@Autowired
	ActivitiesService activitiesService;

	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@PostMapping("/activities")
	public ResponseEntity<?> addActivity(@RequestBody ActivitiesDto activity) {
		return activitiesService.createActivity(activity);
	}

	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@GetMapping("activities/list")
	public ResponseEntity<?> activitiesList() {

		try {
			List<ActivitiesDto> activities = activitiesService.getAll();
			if (activities.isEmpty()) {
				return ResponseEntity.ok("No hay Actividades para mostrar.");
			}
			return ResponseEntity.status(HttpStatus.OK).body(activities);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Por favor intente mas tarde");
		}

	}

	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@GetMapping("/activities/{id}")
    public ResponseEntity<?> getActivitieById(@PathVariable Long id){
	    
		try {
			
			return ResponseEntity.status(HttpStatus.OK).body(activitiesService.getById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Actividad no encontrada");
		}
			
	}

	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@PutMapping("/activities/{id}")
	public ResponseEntity<?>  updateActivities(@PathVariable Long id, @RequestBody ActivitiesDto activities) throws Exception {
		activitiesService.updateActivities(id,activities);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("")
				.buildAndExpand(id)
				.toUri();
		return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
	}
}
