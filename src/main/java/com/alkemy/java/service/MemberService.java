package com.alkemy.java.service;

import java.util.List;

import com.alkemy.java.dto.MemberDto;
import com.alkemy.java.model.Member;

import org.springframework.http.ResponseEntity;

public interface MemberService {

   ResponseEntity<?> saveMember(Member member);

   List<Object> findAllMembers(int pageNo);

   ResponseEntity<?> deleteById(Long id);

   ResponseEntity<?> updateMember(Long id,MemberDto mDto);
}
