package com.alkemy.java.controller;

import com.alkemy.java.dto.request.SlideDto;
import com.alkemy.java.dto.response.SlideResponseDto;
import com.alkemy.java.exception.slide.NullSlideException;
import com.alkemy.java.exception.slide.SlideException;
import com.alkemy.java.model.Slide;
import com.alkemy.java.service.ISlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class SlideController {
    @Autowired
    private ISlideService slideService;
    @Autowired
    SlideException slideException;

    @ResponseBody
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/slides/list")
    public ResponseEntity<List<SlideResponseDto>> getAllSlides(){
        return ResponseEntity.ok(slideService.findAllImage());
    }

    @ResponseBody
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/slides/{id}")
    public ResponseEntity<Slide> getEditSlideId(@PathVariable("id") Long id) throws NullSlideException {
        return ResponseEntity.ok(slideService.findById(id));
    }

    @ResponseBody

    @GetMapping("/delete/{id}")
    public ResponseEntity<SlideResponseDto> getDeleteSlideId(@PathVariable("id") Long id){
        return ResponseEntity.ok(slideService.delete(id));
    }

  
   
    
    @ResponseBody
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/slides/{id}")
    public ResponseEntity<?> UpdateSlide(@PathVariable Long id,
                                        @RequestBody(required = false) SlideDto slideDto){
        try {
            return new ResponseEntity<>(slideService.update(id, slideDto), HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Slide no encontrado");


        }
    }

    @ResponseBody
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/slides")
    public ResponseEntity<List<SlideResponseDto>> getListSlides(){
        return ResponseEntity.ok(slideService.getListSlides());
    }
    
   
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/delete/slides/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
    	try {
			return ResponseEntity.ok(slideService.deleteSlide(id));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Slide no encontrado");
		}
    	
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/slides")
    public String ulploadSlide(@RequestBody SlideDto slidedto) throws IOException {
        slideService.newSlide(slidedto);

        return "guardado correctamente";
    }

}
