package io.parrotsoftware.challenge.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
  @Email(message = "Must be a valid email")
  @Column(nullable = false, unique = true)
  private String email;

  @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
  @Column(nullable = false)
  private String name;

  public User() {}

  public User(Long id, String email, String name) {
    this.id = id;
    this.email = email;
    this.name = name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return this.email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}
