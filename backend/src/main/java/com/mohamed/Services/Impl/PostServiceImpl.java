package com.mohamed.Services.Impl;

import com.mohamed.Entities.Post;
import com.mohamed.Entities.User;
import com.mohamed.Repositories.CategoryRepository;
import com.mohamed.Repositories.PostRepository;
import com.mohamed.Services.PostService;
import com.mohamed.payload.PostRequest;
import com.mohamed.payload.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(Post::getPostResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getPostByCreatedBy(String username) {
        return postRepository.findByAuthor_Username(username);
    }

    @Override
    public List<Post> getPostByCategory(Long id) {
        return postRepository.findByCategory_Id(id);
    }

    @Override
    public Post updatePost(Long id, PostRequest postRequest, User currentUser) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You can't update this post");
        }
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setImage(postRequest.getImage());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id, User currentUser) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You can't delete this post");
        }
        postRepository.delete(post);
    }

    @Override
    public PostResponse addPost(PostRequest postRequest, User currentUser) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setImage(postRequest.getImage());
        post.setAuthor(currentUser);
        post.setCreatedAt(LocalDateTime.now());
        post.setCategory(categoryRepository
                .findById(postRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));
        return postRepository.save(post).getPostResponse();
    }

    @Override
    public Post getPost(Long id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

}
