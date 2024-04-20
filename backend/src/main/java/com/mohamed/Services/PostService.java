package com.mohamed.Services;

import com.mohamed.Entities.Post;
import com.mohamed.Entities.User;
import com.mohamed.payload.PostRequest;
import com.mohamed.payload.PostResponse;

import java.util.List;

public interface PostService {

    List<PostResponse> getAllPosts();

    List<Post> getPostByCreatedBy(String username);

    List<Post> getPostByCategory(Long id);

    Post updatePost(Long id, PostRequest postRequest, User currentUser);

    void deletePost(Long id, User currentUser);

    PostResponse addPost(PostRequest postRequest, User currentUser);

    Post getPost(Long id);
}
