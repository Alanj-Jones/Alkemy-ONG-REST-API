package com.alkemy.java.service.impl;

import com.alkemy.java.dto.MemberDto;
import com.alkemy.java.mapper.MemberMapper;
import com.alkemy.java.model.Member;
import com.alkemy.java.repository.MemberRepository;
import com.alkemy.java.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MemberMapper memberMapper;

    @Value("${default.page.size}")
    private Integer pageSize;


    @Value("${member.pageable.url}")
    private String pageUrl;
    
    
    @Override
    public ResponseEntity<?> saveMember(Member member) {
        try {
            memberRepository.save(member);
            return new ResponseEntity<>(messageSource.getMessage("member.creation.success", null, null), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(messageSource.getMessage("member.creation.failure", null, null), HttpStatus.BAD_REQUEST);
        }
    }    

    @Override
    public List<Object> findAllMembers(int pageNo) {

        Pageable paging =  PageRequest.of(pageNo, pageSize);
        Page<Member> pagedResult = memberRepository.findAll(paging);

        Page<MemberDto> pagedResultDto = memberMapper.convertPageToDto(pagedResult);
               
        if(!pagedResult.hasContent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageSource.getMessage("member.page.not.found", null, null));
        }
        
        List<Object> response = new ArrayList<>();
        response.add(pagedResultDto.getContent());

        if (pagedResult.hasPrevious()) {
            response.add("Anterior: " + pageUrl + (Integer.valueOf(pageNo) - 1) );
        }

        if (pagedResult.hasNext()) {
            response.add("Siguente: " + pageUrl + (Integer.valueOf(pageNo) + 1));
        }
        return response;
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        try {
            memberRepository.deleteById(id);
            return new ResponseEntity<>(messageSource.getMessage("member.deletion.success", null, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("member.deletion.failure", new Object[] {id}, null), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateMember(Long id, MemberDto mDto){
       try {
           Member category = this.findById(id);
           List<MemberDto> list = new ArrayList<MemberDto>();
           list.add(mDto);
           for (MemberDto c : list) {
               if (c.getName() != null) {
                   category.setName(mDto.getName());
               }
               if (c.getImage() != null) {
                   category.setImage(mDto.getImage());
               }
               if (c.getDescription() != null) {
                   category.setDescription(mDto.getDescription());
               }
               if (c.getInstagramUrl() != null) {
                   category.setInstagramUrl(mDto.getInstagramUrl());
               }
               if (c.getFacebookUrl() != null) {
                   category.setFacebookUrl(mDto.getFacebookUrl());
               }

               if (c.getLinkedinUrl() != null) {
                   category.setLinkedinUrl(mDto.getLinkedinUrl());
               }
           }
           memberRepository.save(category);
           return new ResponseEntity<>(messageSource.getMessage("member.update.success", null, null), HttpStatus.OK);
       }catch(Exception e ){
           return new ResponseEntity<>(messageSource.getMessage("member.update.failure", new Object[] {id}, null), HttpStatus.NOT_FOUND);
       }
    }


    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
