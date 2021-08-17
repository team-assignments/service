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
 * Task controller
 */
@RestController
@RequestMapping("/groups/{groupId}/tasks")
public class TaskController {

  private final TaskService service;

  /**
   * constructor for Task.
   * @param service
   */
  public TaskController(TaskService service) {
    this.service = service;
  }


  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Task post(@RequestBody Task task, @PathVariable long groupId, Authentication auth) {

    return service
        .save(task, (User) auth.getPrincipal(), groupId)
        .orElseThrow();
    //TODO return a response entity for a created resource

  }

  @PutMapping(value ="/{taskId}/members/{memberId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean assign(@RequestBody boolean assigned, @PathVariable long groupId, @PathVariable long taskId, @PathVariable long memberId, Authentication auth) {

    return service
        .assign(assigned, groupId, taskId, memberId, (User) auth.getPrincipal())
        .orElseThrow();
  }

  @GetMapping(value ="/{taskId}/members/{memberId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean isAssigned( @PathVariable long groupId, @PathVariable long taskId, @PathVariable long memberId, Authentication auth) {

    return service
        .isAssigned(groupId, taskId, memberId, (User) auth.getPrincipal())
        .orElseThrow();
  }
}
