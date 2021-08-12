package com.alkemy.java.mapper;

import com.alkemy.java.dto.MemberDto;
import com.alkemy.java.model.Member;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    
    public MemberDto convertToDto(Member member) {
        MemberDto dto = new MemberDto(
            member.getName(),
            member.getFacebookUrl(),
            member.getInstagramUrl(),
            member.getLinkedinUrl(),
            member.getImage(),
            member.getDescription()
        );
        
        return dto;
    }

    public Page<MemberDto> convertPageToDto(Page<Member> page) {
        return page.map(this::convertToDto);
    }
    
}