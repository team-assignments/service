package edu.cnm.deepdive.teamassignments.controller;

import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import edu.cnm.deepdive.teamassignments.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller
 */
@RestController
@RequestMapping("users")
public class UserController {

  private final UserService userService;

  /**
   * User controller constructor.
   * @param userService
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * get mapping for user.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return
   */
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me(Authentication auth) {
    return (User) auth.getPrincipal();
  }

  /**
   * post mapping for user.
   * @param user
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) //TODO review with team
  public User post(@RequestBody User user, Authentication auth) {

    return userService.save((User) auth.getPrincipal());
  }

  //TODO no put mapping, dont know if it makes sense to add.

}
