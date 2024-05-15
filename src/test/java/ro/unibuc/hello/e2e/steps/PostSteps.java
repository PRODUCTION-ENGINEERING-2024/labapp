package ro.unibuc.hello.e2e.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ro.unibuc.hello.data.PostEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.service.PostService;
import ro.unibuc.hello.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class PostSteps {

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
     
    @Given("a post exists with Name {string}")
    public void a_post_exists_with_id_and_details(String name) {
        PostService postServiceMock = mock(PostService.class);
        PostEntity post = postServiceMock.getPostByTitle(name);
        when(postServiceMock.getPostByTitle(name)).thenReturn(post);
    }

    @Given("a user exists with UserName {string}")
    public void a_user_exists_with_id_and_details(String username) {
        UserService userServiceMock = mock(UserService.class);
        UserEntity user = userServiceMock.getUserByUserName(username);
        when(userServiceMock.getUserByUserName(username)).thenReturn(user);
    }

    @When("I register user with UserName {string} to post with name {string}")
    public void i_register_user_with_id_to_post_with_id(String username, String title) {
        UserEntity user = userService.getUserByUserName(username);
        PostEntity post = postService.getPostByTitle(title);
        //print user and post
        System.out.println("User: " + user);
        System.out.println("Post: " + post);
        String url = "http://localhost:8080/registerUserToPost/" + user.id + "/" + post.id;
        response = restTemplate.postForEntity(url, null, String.class);
    }

    @Then("the registration should be successful")
    public void the_registration_should_be_successful() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

   
}
