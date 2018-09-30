package com.pociot.hcb.auth.controller;

import com.pociot.hcb.auth.domain.User;
import com.pociot.hcb.auth.service.UserService;
import java.security.Principal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/current", method = RequestMethod.GET)
  public Principal getUser(Principal principal) {
    return principal;
  }

  @RequestMapping(method = RequestMethod.POST)
  public void createUser(User user) {
    userService.create(user);
  }
}
