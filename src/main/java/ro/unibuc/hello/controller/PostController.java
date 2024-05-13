package ro.unibuc.hello.controller;

import javax.management.ConstructorParameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.web.bind.annotation.DeleteMapping;

import ro.unibuc.hello.data.PostEntity;
import ro.unibuc.hello.dto.PostDto;
import ro.unibuc.hello.service.PostService;
import org.springframework.http.HttpStatus;
import ro.unibuc.hello.dto.PostDto;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    MeterRegistry metricsRegistry;

    @GetMapping("/getPost/{id}")
    @ResponseBody
    @Timed(value = "hello.getpost.time", description = "Time taken to get a post")
    @Counted(value = "hello.getpost.count", description = "Times getpost was returned")
    public PostEntity getPost(@PathVariable String id) {
        try {
            return postService.getPost(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/addPost")
    @ResponseBody
    @Timed(value = "hello.addpost.time", description = "Time taken to add a post")
    @Counted(value = "hello.addpost.count", description = "Times addpost was returned")
    public String addPost(@RequestBody PostDto postDto) {
        try {
            return postService.addPost(postDto);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/registerUserToPost/{userId}/{postId}")
    @ResponseBody
    @Timed(value = "hello.registerusertopost.time", description = "Time taken to register a user to a post")
    @Counted(value = "hello.registerusertopost.count", description = "Times registerusertopost was returned")
    public String registerUserToPost(@PathVariable String userId, @PathVariable String postId) {
        try {
            return postService.registerUserToPost(userId, postId);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/deletePost/{id}")
    @ResponseBody
    @Timed(value = "hello.deletepost.time", description = "Time taken to register a delete a post")
    @Counted(value = "hello.deletepost.count", description = "Times deletepost was returned")
    public String deletePostById(@PathVariable String id) {
        try {
            return postService.deletePostById(id);
        } catch (Exception e) {
            throw e;
        }
    }
}