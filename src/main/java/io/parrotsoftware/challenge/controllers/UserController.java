package io.parrotsoftware.challenge.controllers;

import io.parrotsoftware.challenge.errors.MyError;
import io.parrotsoftware.challenge.errors.MyValidationException;
import io.parrotsoftware.challenge.entities.User;
import io.parrotsoftware.challenge.entities.UserRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/users")
  public ResponseEntity<User> create(@Valid @RequestBody User user) {

    User existingUser = userRepository.findFirstByEmail(user.getEmail());
    if (existingUser != null) {
      throw new MyValidationException(new MyError("email", "Email already exists"));
    }
    User createdUser = userRepository.save(user);
    return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);

  }

}
