package com.pociot.hcb.auth.service.security;

import com.pociot.hcb.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public MongoUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return userRepository.findById(s).blockOptional().orElseThrow(() -> new UsernameNotFoundException(s));
  }
}
