package edu.cnm.deepdive.teamassignments.controller;

import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import edu.cnm.deepdive.teamassignments.service.GroupService;
import java.net.URI;
import java.util.List;
import java.util.function.Function;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller for group with Spring annotation mapping.
 */
@RestController
@RequestMapping("/groups")
public class GroupController {

  private final GroupService service;

  /**
   * Constructor for group.
   * @param service - GroupService class, used for repository work.
   */
  public GroupController(GroupService service) {
    this.service = service;
  }

  /**
   * Post mapping for adding a group.
   * @param group group object from group entity class.
   * @param auth  token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return provides new group object via Json.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Group> post(@RequestBody Group group, Authentication auth) {

    group = service.save(group, (User) auth.getPrincipal());

    URI location = WebMvcLinkBuilder
        .linkTo(
            WebMvcLinkBuilder
                .methodOn(GroupController.class)
                .get(group.getId(), auth)
        )
        .toUri();

    return ResponseEntity.created(location).body(group);
  }

  /**
   * Put mapping for membership.
   * @param groupId path variable for group id
   * @param userId path variable for user id
   * @param inGroup boolean valuable used to verify if individual is in group that is added to body of request.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return provides boolean value verifying group membership.
   */
  @PutMapping(value = "/{groupId:\\d+}/members/{userId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean putMembership(@PathVariable long groupId, @PathVariable long userId,
      @RequestBody boolean inGroup, Authentication auth) {

    return service.toggleMembership(groupId, userId, inGroup, (User) auth.getPrincipal());


  }

  /**
   * get mapping for checking membership.
   * @param groupId path variable for group id
   * @param userId path variable for user id
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return provides userId boolean value via JSON.
   */
  @GetMapping(value = "/{groupId:\\d+}/members/{userId:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean getMembership(@PathVariable long groupId, @PathVariable long userId, Authentication auth) {

    return service.checkMembership(groupId, userId, (User) auth.getPrincipal());


  }

  /**
   * get mapping for group.
   * @param id group id.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return provides Group object via JSON.
   */
  @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Group get(@PathVariable long id, Authentication auth) {

    return service.get(id, (User) auth.getPrincipal()).orElseThrow();

  }

  /**
   * put mapping for renaming group.
   * @param id parent group id.
   * @param name String value in body used to update name.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return provides String object via Json.
   */
  @PutMapping(value = "/{id:\\d+}/name", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public String replaceName(@PathVariable long id, @RequestBody String name, Authentication auth) {

    return service.rename(id, name, (User) auth.getPrincipal())
        .map(Group::getName)
        .orElseThrow();
  }

  /**
   *
   * @param ownedOnly Iterable list for owned groups.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return Group Iterable via Json.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Group> getGroups(@RequestParam(required = false, defaultValue = "false") boolean ownedOnly, Authentication auth) {

    return service.getGroups(ownedOnly, (User) auth.getPrincipal());

  }


  /**
   * delete mapping for group.
   * @param id group id to delete.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   */
  @DeleteMapping(value = "/{id:\\d+}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id, Authentication auth) {
    service.delete(id, (User) auth.getPrincipal());
  }

}
