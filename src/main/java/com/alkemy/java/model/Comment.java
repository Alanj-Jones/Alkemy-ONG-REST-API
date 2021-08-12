package com.alkemy.java.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne()
    @JoinColumn(name="news_id")
    private News news;
  
    @NotNull
    @Column(columnDefinition="TEXT")
    private String body;

    @NotNull
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime modificationDate;

}
