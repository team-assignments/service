package edu.cnm.deepdive.teamassignments.service;

import edu.cnm.deepdive.teamassignments.model.dao.TaskRepository;
import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.Task;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Task service class
 */
@Service
public class TaskService {

  private final TaskRepository repository;

  /**
   * Taks service repository.
   * @param repository
   */
  @Autowired
  public TaskService(TaskRepository repository) {
    this.repository = repository;
  }

  /**
   * Save a new task.
   * @param task
   * @param user
   * @return
   */
  public Task save(Task task, User user) {
    task.setUser(user);
    return repository.save(task);
  }


  /**
   * Look up tasks for a given user.
   * @param id
   * @return
   */
  public Optional<Task> get(long id) {   //TODO review with team
    return repository.findById(id)
        .map((task) -> {
          if (task.getUser().getId().equals(task.getId())) {
            return task;
          } else {
            return null;
          }
        });
  }

  /**
   * Mark a task as completed.
   * @param id
   * @param user
   */
  public void completed(long id, User user) {   //TODO review with team

    repository.findById(id)
        .map((task) -> {
          if(task.getUser().getId().equals(user.getId())) {
            return task;
          } else {
            return null;
          }
        })
        .map((task) -> {
          task.setCompleted(true);
          return repository.save(task);
        });

  }


  /**
   * Delete a task
   * @param id
   * @param user
   * @param group
   */
  public void delete(long id, User user, Group group) {

    repository.findById(id)
        .map((task) -> {
          if(task.isConfirmedComplete() && group.getOwner().getId().equals(user.getId())) {             // if(group.getOwner().getId().equals(user.getId())) {
            return task;
          } else {
            return null;
          }
        })
        .ifPresent(repository::delete);

  }



}
