package io.parrotsoftware.challenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.parrotsoftware.challenge.entities.User;
import io.parrotsoftware.challenge.entities.UserRepository;
import io.parrotsoftware.challenge.utils.Mocks;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerCreateTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserRepository userRepository;
 
  @Test
  public void userSuccessfullyCreated() throws Exception {

    User user = Mocks.mockUser();
    when(userRepository.findFirstByEmail(anyString())).thenReturn(null);
    when(userRepository.save(any(User.class))).thenReturn(user);
    this.mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is(user.getName())))
        .andExpect(jsonPath("$.email", is(user.getEmail())));

  }

  @Test
  public void userAlreadyExists() throws Exception {

    User user = Mocks.mockUser();
    when(userRepository.findFirstByEmail(anyString())).thenReturn(user);
    this.mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors[0].field", is("email")))
        .andExpect(jsonPath("$.errors[0].message", is("Email already exists")));

  }

  @Test
  public void userIsInvalid() throws Exception {

    User user = Mocks.mockUser();
    user.setEmail("notAnEmail");
    this.mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors[0].field", is("email")))
        .andExpect(jsonPath("$.errors[0].message", is("Must be a valid email")));

  }

}

