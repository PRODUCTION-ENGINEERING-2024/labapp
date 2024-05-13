package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;

import ro.unibuc.hello.data.PostEntity;
import ro.unibuc.hello.data.PostRepository;

import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.data.UserEntity;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableMongoRepositories(basePackageClasses = {InformationRepository.class,   UserRepository.class, PostRepository.class})
public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));
				
		userRepository.deleteAll();
		userRepository.save(new UserEntity("HAU","Tudor",21,"ala"));
		userRepository.save(new UserEntity("Doe","John",21,"johndoe"));

		postRepository.deleteAll();
		postRepository.save(
			new PostEntity(
				"Football",
				"Cluj",
				LocalDateTime.now(),
				10
			)
		);
		postRepository.save(
			new PostEntity(
				"Fotbal Crangasi",
				"Parc Crangasi",
				LocalDateTime.now(),
				18
			)
		);
	}

}
