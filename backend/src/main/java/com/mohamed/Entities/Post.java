package com.mohamed.Entities;

import com.mohamed.payload.PostResponse;
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
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)

    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    public PostResponse getPostResponse() {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(this.id);
        postResponse.setTitle(this.title);
        postResponse.setContent(this.content);
        postResponse.setImage(this.image);
        postResponse.setCategoryId(this.category.getId());
        postResponse.setCategoryName(this.category.getName());
        postResponse.setAuthor(this.author.getUsername());
        postResponse.setCreatedAt(this.createdAt);
        return postResponse;
    }


}
