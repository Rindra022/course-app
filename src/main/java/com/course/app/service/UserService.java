package com.course.app.service;

import com.course.app.mapper.UserMapper;
import com.course.app.model.User;
import com.course.app.repository.UserRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserMapper mapper;
  private final UserRepository repository;

  public User getById(UUID id) {
    return mapper.toModel(
        repository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
  }
}
