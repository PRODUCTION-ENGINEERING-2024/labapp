package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.PostRepository;
import ro.unibuc.hello.dto.PostDto;
import ro.unibuc.hello.data.PostEntity;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

@Component
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public PostEntity getPost(String id) throws Exception {
        return postRepository.findById(id).orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
    }

    public String addPost(PostDto post) {
        PostEntity postEntity = new PostEntity(
            post.getTitle(), 
            post.getLocation(), 
            LocalDateTime.now(),
            post.getTotalNumberOfPlayers()
        );
        postRepository.save(postEntity);
        return "Post added";
    }

    public String deletePostById(String id) {
        postRepository.deleteById(id);
        return "Post deleted";
    }
}