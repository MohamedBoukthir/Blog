package com.mohamed.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String image;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id" , nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_id" , nullable = false)
    private User author;

    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


}
