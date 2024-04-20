package com.mohamed.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String image;
    private Long categoryId;
    private String categoryName;
    private String author;
    private LocalDateTime createdAt;

}
