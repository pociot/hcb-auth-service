package com.pociot.hcb.auth.service;

import com.pociot.hcb.auth.domain.User;
import com.pociot.hcb.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
  private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;

  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void create(User user) {
    Mono<User> existingUser = userRepository.findById(user.getUsername());
    existingUser.subscribe(
        value -> {
          throw new IllegalArgumentException("user already exists: " + value.getUsername());
        },
        error -> log.error("Error while creating user, {}", error.getCause().getLocalizedMessage()),
        () -> {
          String hash = encoder.encode(user.getPassword());
          user.setPassword(hash);

          userRepository
              .save(user)
              .doOnSuccess((savedUser) -> log.info("User {} has been created.", savedUser.getUsername()))
              .doOnError((error) -> log.error("Error creating user, {}", error.getCause().getLocalizedMessage()));
        }
    );
  }
}
