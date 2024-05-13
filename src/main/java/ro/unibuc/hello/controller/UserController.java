package ro.unibuc.hello.controller;

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



import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.UserDto;
import ro.unibuc.hello.service.UserService;
import org.springframework.http.HttpStatus;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    MeterRegistry metricsRegistry;
  
    @GetMapping("/getUser/{id}")
    @ResponseBody
    @Timed(value = "hello.getuser.time", description = "Time taken to return a user")
    @Counted(value = "hello.getuser.count", description = "Times getuser was returned")
    public UserEntity getUser(@PathVariable String id){
       try {
           return userService.getUser(id);
       } catch (Exception e) {
           return null;   
       }
    }

    @PostMapping("/addUser")
    @ResponseBody
    @Timed(value = "hello.adduser.time", description = "Time taken to add a user")
    @Counted(value = "hello.adduser.count", description = "Times adduser was returned")
    public String addUser(@RequestBody UserDto userDto){  
        try {
            return userService.addUser(userDto);
        } catch (Exception e) {
            throw e;
        }
    }


    @DeleteMapping("/deleteUser/{id}")
    @ResponseBody
    @Timed(value = "hello.deleteuser.time", description = "Time taken to delete a user")
    @Counted(value = "hello.deleteuser.count", description = "Times deleteuser was returned")
    public String deleteUserById(@PathVariable String id) {
        try {
            return userService.deleteUserById(id);
        } catch (Exception e) {
            throw e;
        }
    }
}
