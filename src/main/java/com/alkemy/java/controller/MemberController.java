package com.alkemy.java.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.alkemy.java.dto.MemberDto;
import com.alkemy.java.model.Member;
import com.alkemy.java.service.MemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private static final String CREATE = "Create";
    private static final int CODE_CREATE = 201;

    @Autowired
    private MemberService memberService;

    @ApiResponses( value = {
            @ApiResponse( code = CODE_CREATE , message = CREATE )}
    )
    @ApiOperation(value = "${memberController.create}")
    @ApiParam(name = "name" , example = "description..",required = true)
    @PostMapping("/members")
    public ResponseEntity<?> addMember(@RequestBody @Valid Member member, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<String>();
            result.getAllErrors().stream().forEach((e) -> errors.add(e.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return memberService.saveMember(member);
    }

    @ApiOperation(value = "$memberController.allMember")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/members")
    public ResponseEntity<?> getMembers(@RequestParam(name = "page", defaultValue = "0") Integer pageNo){
        return new ResponseEntity<>(memberService.findAllMembers(pageNo),HttpStatus.OK);
    }

    @ApiOperation(value = "${memberController.delete}")
    @DeleteMapping("/members/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable(name = "id") Long id) {
        return memberService.deleteById(id);
    }


    @ApiOperation(value = "${memberController.update}")
    @PutMapping("/members/{id}")
    public ResponseEntity<?>  updateCategories(@PathVariable Long id, @RequestBody MemberDto mDto){
        return new ResponseEntity<>(memberService.updateMember(id, mDto), (HttpStatus.ACCEPTED));
        }

}
