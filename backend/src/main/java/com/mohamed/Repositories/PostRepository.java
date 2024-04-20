package com.mohamed.Repositories;

import com.mohamed.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor_Username(String username);
    List<Post> findByCategory_Id(Long id);
}
