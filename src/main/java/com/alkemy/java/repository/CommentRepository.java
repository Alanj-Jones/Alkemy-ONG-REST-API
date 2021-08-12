package com.alkemy.java.repository;

import java.util.List;

import com.alkemy.java.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

    List<Comment> findAllByOrderByCreationDate();
    List<Comment> findAllByNewsId(Long id);
}
