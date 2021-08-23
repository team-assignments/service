package edu.cnm.deepdive.teamassignments.service;

import edu.cnm.deepdive.teamassignments.model.dao.GroupRepository;
import edu.cnm.deepdive.teamassignments.model.dao.TaskRepository;
import edu.cnm.deepdive.teamassignments.model.dao.UserRepository;
import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.Task;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Task service class
 */
@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  /**
   * Taks service repository.
   *
   * @param repository
   * @param groupRepository
   * @param userRepository
   */
  @Autowired
  public TaskService(TaskRepository repository,
      GroupRepository groupRepository,
      UserRepository userRepository) {
    this.taskRepository = repository;
    this.groupRepository = groupRepository;
    this.userRepository = userRepository;
  }

  /**
   * Save a new task.
   *
   * @param task
   * @param user
   * @return
   */
  public Optional<Task> save(Task task, User user, long groupId) {
    return groupRepository
        .findById(groupId)
        .map((group) -> (group.getOwner().getId().equals(user.getId()))
            ? group
            : null
        )
        .map((group) -> {

          task.setUser(user);
          task.setGroup(group);
          return taskRepository.save(task);

        });
  }


  /**
   * Look up tasks for a given user.
   *
   * @param id
   * @return
   */
  public Optional<Task> get(long id, long groupId, User user) {
    return taskRepository.findById(id)
        .map((task) -> {
          if (task.getId().equals(id) && task.getUser().equals(user) && task.getGroup().getId().equals(groupId)) {
            return task;
          } else {
            return null;
          }
        });
  }

  /**
   * Mark a task as completed.
   *
   * @param id
   * @param user
   */
  public void completed(long id, User user) {   //TODO review with team

    taskRepository.findById(id)
        .map((task) -> {
          if (task.getUser().getId().equals(user.getId())) {
            return task;
          } else {
            return null;
          }
        })
        .map((task) -> {
          task.setCompleted(true);
          return taskRepository.save(task);
        });

  }


  /**
   * Delete a task
   *
   * @param id
   * @param user
   * @param group
   */
  public void delete(long id, User user, Group group) {

    taskRepository.findById(id)
        .map((task) -> {
          if (task.isConfirmedComplete() && group.getOwner().getId().equals(
              user.getId())) {             // if(group.getOwner().getId().equals(user.getId())) {
            return task;
          } else {
            return null;
          }
        })
        .ifPresent(taskRepository::delete);

  }

  public Optional<Boolean> assign(boolean assigned, long groupId, long taskId, long memberId,
      User user) {

    return groupRepository
        .findById(groupId)
        .map((group) -> group.getOwner().getId().equals(user.getId())
            ? group
            : null
        )
        .flatMap((group) -> userRepository.findById(memberId)
            .map((member) -> member.getGroups().contains(group)
                ? member
                : null
            )
        )
        .flatMap((member) -> taskRepository.findById(taskId)
            .map((task) -> task.getGroup().getId().equals(groupId)
                ? task
                : null
            )
            .map((task) -> {
              if (assigned) {
                if (!member.equals(task.getUser())) {
                  task.setUser(member);
                }
              } else if (member.equals(task.getUser())) {
                task.setUser(null);
              }
              return taskRepository.save(task);
            })
        )
        .map((task) -> assigned);

  }

  public Optional<Boolean> isAssigned(long groupId, long taskId, long memberId, User user) {

    return groupRepository
        .findById(groupId)
        .map((group) -> group.getOwner().equals(user) || user.getGroups().contains(group)
            ? group
            : null
        )
        .flatMap((group) -> userRepository.findById(memberId)
            .map((member) -> member.getGroups().contains(group)
                ? member
                : null
            )
        )
        .flatMap((member) -> taskRepository.findById(taskId)
            .map((task) -> task.getGroup().getId().equals(groupId)
                ? task
                : null
            )
            .map((task) -> task.getUser() != null && task.getUser().equals(member))
        );


  }

  public Iterable<Task> getTasks(long groupId, User user) {
    return groupRepository
        .findById(groupId)
        .map((group) -> group.getOwner().equals(user) || user.getGroups().contains(group)
            ? group
            : null
        )
        .map(Group::getTasks)
        .orElseThrow();
  }

}
