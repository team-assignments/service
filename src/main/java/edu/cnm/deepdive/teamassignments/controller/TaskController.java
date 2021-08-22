package edu.cnm.deepdive.teamassignments.controller;


import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.Task;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import edu.cnm.deepdive.teamassignments.service.TaskService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for task with spring annotation mapping.
 */
@RestController
@RequestMapping("/groups/{groupId}/tasks")
public class TaskController {

  private final TaskService service;

  /**
   * constructor for Task.
   * @param service TaskService class, used for repository work.
   */
  public TaskController(TaskService service) {
    this.service = service;
  }


  /**
   *
   * @param task Task object from task entity class.
   * @param groupId parent group id.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return Task object via json.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Task post(@RequestBody Task task, @PathVariable long groupId, Authentication auth) {

    return service
        .save(task, (User) auth.getPrincipal(), groupId)
        .orElseThrow();



  }

  /**
   *
   * @param assigned boolean value verifying assignment of group.
   * @param groupId parent group id.
   * @param taskId task id of task within the group.
   * @param memberId member id of member within the group.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return provide boolean value verifying ownership of task.
   */
  @PutMapping(value ="/{taskId}/members/{memberId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean assign(@RequestBody boolean assigned, @PathVariable long groupId, @PathVariable long taskId, @PathVariable long memberId, Authentication auth) {

    return service
        .assign(assigned, groupId, taskId, memberId, (User) auth.getPrincipal())
        .orElseThrow();
  }

  /**
   *
   * @param groupId parent group id.
   * @param taskId task id of task within the group.
   * @param memberId member id of member within the group.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return provide assignment verification of task.
   */
  @GetMapping(value ="/{taskId}/members/{memberId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean isAssigned( @PathVariable long groupId, @PathVariable long taskId, @PathVariable long memberId, Authentication auth) {

    return service
        .isAssigned(groupId, taskId, memberId, (User) auth.getPrincipal())
        .orElseThrow();
  }

  /**
   *
   * @param groupId parent group id.
   * @param auth token for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
   * @return provide Iterable Task Object.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Task> getAll(@PathVariable long groupId, Authentication auth) {
    return service.getTasks(groupId, (User) auth.getPrincipal());
  }

}
