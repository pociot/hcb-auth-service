package com.pociot.hcb.auth.config;

import com.pociot.hcb.auth.domain.User;
import com.pociot.hcb.auth.repository.UserRepository;
import com.pociot.hcb.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

  private final Logger log = LoggerFactory.getLogger(UserCommandLineRunner.class);

  private final UserRepository userRepository;

  public UserCommandLineRunner(UserRepository userRepository) {
    this.userRepository= userRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    User kamil = new User();
    kamil.setUsername("kamil");
    kamil.setPassword(new BCryptPasswordEncoder().encode("password"));
    userRepository.save(kamil).block();
    log.info("User created with username {}", kamil.getUsername());
  }
}
