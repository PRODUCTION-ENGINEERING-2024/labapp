package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ro.unibuc.hello.data.PostEntity;
import ro.unibuc.hello.dto.PostDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.PostService;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
public class PostControllerTest {
    
    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); 
    }

    @Test
    void testGetPost() throws Exception {

        PostEntity postEntity = new PostEntity("Fotbal Crangasi", "Parc Crangasi",  LocalDateTime.now(), 18);
        when(postService.getPost("1")).thenReturn(postEntity);


        MvcResult result = mockMvc.perform(get("/getPost/1"))
                    .andExpect(status().isOk())
                    .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        System.out.println(objectMapper.writeValueAsString(postEntity));
        assertEquals(objectMapper.writeValueAsString(postEntity), result.getResponse().getContentAsString());
    }

    @Test
    void testAddPost() throws Exception {

        PostDto postDto = new PostDto("Fotbal Crangasi", "Parc Crangasi", 18);
        when(postService.addPost(postDto)).thenReturn("Post added");

        MvcResult result = mockMvc.perform(post("/addPost")
                .content(objectMapper.writeValueAsString(postDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertEquals("Post added", response);
    }

    @Test
    void testDeletePostById() throws Exception {
        when(postService.deletePostById("1")).thenReturn("Post deleted");

        MvcResult result = mockMvc.perform(delete("/deletePost/1"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertEquals("Post deleted", response);
    }

    @Test
    void  testRegisterUserToPost() throws Exception {
        when(postService.registerUserToPost("1", "1")).thenReturn("User registered to post");

        MvcResult result = mockMvc.perform(post("/registerUserToPost/1/1"))
            .andExpect(status().isOk())
            .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals("User registered to post", response);
    }

    @Test
    void testDeletePostByIdNotFound() throws Exception {
        when(postService.deletePostById("1")).thenThrow(new EntityNotFoundException("Post not found with id: 1"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> postController.deletePostById("1"), 
            "Expected deletePostById() to throw EntityNotFoundException, but it didn't");
        
        assertTrue(exception.getMessage().contains("1"));
    }
}
