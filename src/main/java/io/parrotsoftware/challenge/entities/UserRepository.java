package io.parrotsoftware.challenge.entities;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
  User findFirstByEmail(String email);
}
