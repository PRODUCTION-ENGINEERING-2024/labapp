package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.PostDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.PostService;
import ro.unibuc.hello.service.UserService;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.Month;

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
    }

    @Test
    void testGetPost() throws Exception {
        //Arrange
        PostEntity postEntity = new PostEntity("Fotbal Crangasi", "Parc Crangasi",  LocalDateTime.of(
            2024, Month.APRIL, 24, 14, 30, 00), 18);
        when(postService.getPost("1")).thenReturn(postEntity);

        //Act
        MvcResult result = mockMvc.perform(get("/getPost/1"))
                    .andExpect(status().isOk())
                    .andReturn();

        assertEquals(objectMapper.writeValueAsString(postEntity), result.getResponse().getContentAsString());
    }

    @Test
    void testAddPost() throws Exception {
        // Arrange
        PostDto postDto = new PostDto("Fotbal Crangasi", "Parc Crangasi", 18);
        when(postService.addPost(postDto)).thenReturn("Post added");
        // Act
        MvcResult result = mockMvc.perform(post("/addPost")
                .content(objectMapper.writeValueAsString(postDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        // Assert
        assertEquals("Post added", response);
    }

    @Test
    void testDeletePostById() throws Exception {
        PostEntity postEntity = new PostEntity("Fotbal Crangasi", "Parc Crangasi",  LocalDateTime.of(
            2024, Month.APRIL, 24, 14, 30, 00), 18);
        when(postService.deletePostById("1")).thenReturn("Post deleted");

        MvcResult result = mockMvc.perform(delete("/deletePost/1"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        // Assert
        assertEquals("Post deleted", response);
    }

    @Test
    void  testRegisterUserToPost() throws Exception {
        PostEntity postEntity = new PostEntity("Fotbal Crangasi", "Parc Crangasi",  LocalDateTime.of(
            2024, Month.APRIL, 24, 14, 30, 00), 18);
        UserEntity userEntity = new UserEntity("John", "Doe", 30, "johndoe");
        when(postService.registerUserToPost("1", "1")).thenReturn("User registered to post");

        MvcResult result = mockMvc.perform(post("/registerUserToPost/1/1"))
            .andExpect(status().isOk())
            .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals("User registered to post", response);
    }

    @Test
    void testDeletePostByIdNotFound() throws Exception {
        // Arrange
        when(postService.deletePostById("1")).thenThrow(new EntityNotFoundException("Post not found with id: 1"));

        // Act 
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> postController.deletePostById("1"), 
            "Expected deletePostById() to throw EntityNotFoundException, but it didn't");
        
        //Assert
        assertTrue(exception.getMessage().contains("1"));
    }
}
