package edu.cnm.deepdive.teamassignments.controller;

import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import edu.cnm.deepdive.teamassignments.service.UserService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller class
 */
@RestController
@RequestMapping("users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * get mapping for the User making the request.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return User object casted from auth token.
   */
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me(Authentication auth) {
    return (User) auth.getPrincipal();
  }

  /**
   * Post mapping for User object.
   * @param user User object from user entity class.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return User object via Json.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> post(@RequestBody User user, Authentication auth) {

    user = userService.save((User) auth.getPrincipal());

    URI location = WebMvcLinkBuilder
        .linkTo(
            WebMvcLinkBuilder
                .methodOn(UserController.class)
                .me(auth)
        )
        .toUri();

    return ResponseEntity.created(location).body(user);
  }

  //TODO no put mapping, dont know if it makes sense to add.

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<User> get(Authentication auth) {
    return userService.getAll();
  }

}
