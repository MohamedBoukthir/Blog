package com.mohamed.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id" , nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "post_id" , nullable = false)
    private Post post;


}
