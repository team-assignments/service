package edu.cnm.deepdive.teamassignments.controller;


import edu.cnm.deepdive.teamassignments.model.entity.Task;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import edu.cnm.deepdive.teamassignments.service.TaskService;
import java.net.URI;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
   *
   * @param service TaskService class, used for repository work.
   */
  public TaskController(TaskService service) {
    this.service = service;
  }


  /**
   * post mapping for adding a task.
   *
   * @param task    Task object from task entity class.
   * @param groupId parent group id.
   * @param auth    token for an authenticated principal once the request has been processed by the
   *                AuthenticationManager.authenticate(Authentication) method.
   * @return Task object via json.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Task> post(@RequestBody Task task, @PathVariable long groupId,
      Authentication auth) {

    return service
        .save(task, (User) auth.getPrincipal(), groupId)
        .map((t) -> {
          URI location = WebMvcLinkBuilder
              .linkTo(
                  WebMvcLinkBuilder
                      .methodOn(TaskController.class)
                      .get(groupId, t.getId(), auth)
              )
              .toUri();
          return ResponseEntity.created(location).body(t);
        })
        .orElseThrow();
    //TODO return a response entity for a created resource

  }

  @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Task get(@PathVariable long groupId, @PathVariable long taskId, Authentication auth) {
    return service
        .get(groupId, taskId, (User) auth.getPrincipal())
        .orElseThrow();
  }

  @DeleteMapping(value = "/{taskId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long groupId, @PathVariable long taskId, Authentication auth) {
    service
        .delete(groupId, taskId, (User) auth.getPrincipal())
        .orElseThrow();
  }

  @PutMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Task put(@PathVariable long groupId, @PathVariable long taskId, @RequestBody Task task,
      Authentication auth) {
    return service
        .put(groupId, taskId, task, (User) auth.getPrincipal())
        .orElseThrow();
  }

  /**
   * boolean value meant to assign a task to a user.
   *
   * @param assigned boolean value verifying assignment of group.
   * @param groupId  parent group id.
   * @param taskId   task id of task within the group.
   * @param memberId member id of member within the group.
   * @param auth     token for an authenticated principal once the request has been processed by the
   *                 AuthenticationManager.authenticate(Authentication) method.
   * @return provide boolean value verifying ownership of task.
   */
  @PutMapping(value = "/{taskId}/members/{memberId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean assign(@RequestBody boolean assigned, @PathVariable long groupId,
      @PathVariable long taskId, @PathVariable long memberId, Authentication auth) {

    return service
        .assign(assigned, groupId, taskId, memberId, (User) auth.getPrincipal())
        .orElseThrow();
  }

  /**
   * Get mapping for isAssigned boolean value.
   *
   * @param groupId  parent group id.
   * @param taskId   task id of task within the group.
   * @param memberId member id of member within the group.
   * @param auth     token for an authenticated principal once the request has been processed by the
   *                 AuthenticationManager.authenticate(Authentication) method.
   * @return provide assignment verification of task.
   */
  @GetMapping(value = "/{taskId}/members/{memberId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean isAssigned(@PathVariable long groupId, @PathVariable long taskId,
      @PathVariable long memberId, Authentication auth) {

    return service
        .isAssigned(groupId, taskId, memberId, (User) auth.getPrincipal())
        .orElseThrow();
  }

  /**
   * gets all tasks available for that specific group.
   *
   * @param groupId parent group id.
   * @param auth    token for an authenticated principal once the request has been processed by the
   *                AuthenticationManager.authenticate(Authentication) method.
   * @return provide Iterable Task Object.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Task> getAll(@PathVariable long groupId, Authentication auth) {
    return service.getTasks(groupId, (User) auth.getPrincipal());
  }

}
