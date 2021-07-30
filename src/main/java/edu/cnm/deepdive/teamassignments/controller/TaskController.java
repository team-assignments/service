package edu.cnm.deepdive.teamassignments.controller;


import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.Task;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import edu.cnm.deepdive.teamassignments.service.TaskService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Task controller
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService service;

  /**
   * constructor for Task.
   * @param service
   */
  public TaskController(TaskService service) {
    this.service = service;
  }

  /**
   * post mapping for task.
   * @param task
   * @param user
   * @param group
   * @param date
   * @param auth
   * @return
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Task post(@RequestBody Task task, User user, Group group, Date date, Authentication auth) {

    return service.save(task, (User) auth.getPrincipal());

  }


}
