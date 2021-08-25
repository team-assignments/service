package edu.cnm.deepdive.teamassignments.controller;

import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import edu.cnm.deepdive.teamassignments.service.GroupService;
import java.util.List;
import java.util.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
 * controller for group.
 */
@RestController
@RequestMapping("/groups")
public class GroupController {

  private final GroupService service;

  /**
   * constructor for group
   * @param service
   */
  public GroupController(GroupService service) {
    this.service = service;
  }

  /**
   * Post mapping for group.
   * @param group
   * @param auth
   * @return
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Group post(@RequestBody Group group, Authentication auth) {

    return service.save(group, (User) auth.getPrincipal());

  }

  /**
   * Put mapping for membership.
   * @param groupId
   * @param userId
   * @param inGroup
   * @param auth
   * @return
   */
  @PutMapping(value = "/{groupId:\\d+}/members/{userId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean putMembership(@PathVariable long groupId, @PathVariable long userId,
      @RequestBody boolean inGroup, Authentication auth) {

    return service.toggleMembership(groupId, userId, inGroup, (User) auth.getPrincipal());


  }

  /**
   * put mapping for checking membership.
   * @param groupId
   * @param userId
   * @param auth
   * @return
   */
  @GetMapping(value = "/{groupId:\\d+}/members/{userId:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean getMembership(@PathVariable long groupId, @PathVariable long userId, Authentication auth) {

    return service.checkMembership(groupId, userId, (User) auth.getPrincipal());


  }

  /**
   * get mapping for group.
   * @param id
   * @param auth
   * @return
   */
  @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Group get(@PathVariable long id, Authentication auth) {

    return service.get(id, (User) auth.getPrincipal()).orElseThrow();

  }

  /**
   * put mapping for renaming group.
   * @param id
   * @param name
   * @param auth
   * @return
   */
  @PutMapping(value = "/{id:\\d+}/name", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String replaceName(@PathVariable long id, @RequestBody String name, Authentication auth) {

    return service.rename(id, name, (User) auth.getPrincipal())
        .map(Group::getName)
        .orElseThrow();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Group> getGroups(@RequestParam(required = false, defaultValue = "false") boolean ownedOnly, Authentication auth) {

    return service.getGroups(ownedOnly, (User) auth.getPrincipal());

  }


  /**
   * delete mapping for group.
   * @param id
   * @param auth
   */
  @DeleteMapping(value = "/{id:\\d+}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id, Authentication auth) {
    service.delete(id, (User) auth.getPrincipal());
  }

}
