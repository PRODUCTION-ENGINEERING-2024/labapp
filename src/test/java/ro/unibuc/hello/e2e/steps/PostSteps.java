package ro.unibuc.hello.e2e.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.unibuc.hello.data.PostEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.service.PostService;
import ro.unibuc.hello.service.UserService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.sql.Timestamp;
import com.mongodb.internal.connection.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@CucumberContextConfiguration
@SpringBootTest()
public class PostSteps {

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
     
    @Given("a post exists with ID {string} and details {string}, {string}, {string}, {int}")
    public void a_post_exists_with_id_and_details(String id, String title, String location, String dateTime, Integer maxParticipants) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        PostEntity post = new PostEntity(title, location, localDateTime, maxParticipants);
        when(postService.getPost(id)).thenReturn(post);
    }

    @Given("a user exists with ID {string} and details {string}, {string}, {int}, {string}")
    public void a_user_exists_with_id_and_details(String id, String firstName, String lastName, Integer age, String username) {
        UserEntity user = new UserEntity(firstName, lastName, age, username);
        when(userService.getUser(id)).thenReturn(user);
    }

    @When("I register user with ID {string} to post with ID {string}")
    public void i_register_user_with_id_to_post_with_id(String userId, String postId) {
        String url = "http://localhost:8080/registerUserToPost/" + postId + "/" + userId;
        response = restTemplate.postForEntity(url, null, String.class);
    }

    @Then("the registration should be successful")
    public void the_registration_should_be_successful() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("the response should be {string}")
    public void the_response_should_be(String expectedResponse) {
        assertEquals(expectedResponse, response.getBody());
    }
}
