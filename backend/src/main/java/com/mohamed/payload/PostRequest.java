package com.mohamed.payload;

import lombok.Data;

@Data
public class PostRequest {

    private String title;
    private String content;
    private String image;
    private Long categoryId;
}
