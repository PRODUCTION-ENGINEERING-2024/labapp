package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.UserDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.data.UserEntity;


@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    
    public UserEntity getUser(String id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
    
    public UserEntity getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
    public String addUser(UserDto user) {
        UserEntity userEntity = new UserEntity(user.getLastName(), user.getFirstName(), user.getAge(),user.getUserName());
        userRepository.save(userEntity);
        return "User added";
    }

    public String deleteUserById(String id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        return "User deleted";
    }
}
