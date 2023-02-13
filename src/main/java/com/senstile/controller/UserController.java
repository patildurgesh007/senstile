package com.senstile.controller;

import com.senstile.persistance.User;
import com.senstile.persistance.UserRepository;
import com.senstile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.ConstraintViolationException;
import java.util.List;


@RestController
@EnableSwagger2
@RequestMapping("/api")
public class UserController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody User user){
        userService.save(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }
}
