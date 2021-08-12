package com.alkemy.java.service.impl;

import com.alkemy.java.dto.CommentDtoCreate;
import com.alkemy.java.model.Comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alkemy.java.dto.CommentDto;
import com.alkemy.java.model.News;
import com.alkemy.java.model.TypeRoles;
import com.alkemy.java.model.User;
import com.alkemy.java.repository.CommentRepository;
import com.alkemy.java.repository.NewsRepository;
import com.alkemy.java.repository.UserRepository;
import com.alkemy.java.security.JwtRequestFilter;
import com.alkemy.java.security.JwtTokenService;
import com.alkemy.java.service.CommentService;

import com.alkemy.java.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.alkemy.java.mapper.CommentMapper;

import javax.servlet.http.HttpServletRequest;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RolService service;
    @Autowired
    private NewsRepository repositoryNew;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;

    @Override
    public void updateComment(Long id, CommentDto commentDto){
        Comment comments= this.findById(id);

        comments.setBody(commentDto.getBody());
        commentRepository.save(comments);
    }

    @Override
    public Comment findById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<CommentDto> listComments() {

             return commentRepository.findAllByOrderByCreationDate()
            .stream()
            .map(CommentMapper::toDto)
            .collect(Collectors.toList());
         
    }


	@Override
	public List<CommentDto> listByPost(Long id) {
		
		return commentRepository.findAllByNewsId(id)
				.stream()
	            .map(CommentMapper::toDto)
	            .collect(Collectors.toList());
	}
    
    

    @Override
    public ResponseEntity<?> deleteComent(Long id, HttpServletRequest request) {
        String token = JwtRequestFilter.getJwtFromRequest(request).get();
        String userName = jwtTokenService.getUsernameFromToken(token);
        User user= userRepository.findByEmail(userName);
        User userdefatult= new User();
        userdefatult.setRoleId(service.getByRolName(TypeRoles.ADMINISTRATOR).get());
         Comment comment= commentRepository.getOne(id);
         User usercomparation= comment.getUser();

        if((user == usercomparation)||(user.getRoleId().equals(userdefatult.getRoleId()))){
           commentRepository.deleteById(id);
            return new ResponseEntity<>(messageSource.getMessage("comments.delete.success", null, null), HttpStatus.OK);

        }else {
            return new ResponseEntity<>(messageSource.getMessage("comments.delete.fail", null, null), HttpStatus.UNAUTHORIZED);

        }


    }

    @Override
    public String createComment(CommentDtoCreate comment) throws Exception {

        try {
            Optional<User> userOptional = userRepository.findById(comment.getIdUser());
            Optional<News>  newOptional = repositoryNew.findById(comment.getIdNews());
            Comment commentDAO = new Comment();
            commentDAO.setBody(comment.getBody());
            commentDAO.setNews(newOptional.get());
            commentDAO.setUser(userOptional.get());
            commentRepository.save(commentDAO);
        } catch (Exception e) {
            return messageSource.getMessage("comments.creation.failure",null,null);
        }

        return messageSource.getMessage("comments.creation.success",null,null);

    }



}
