package edu.cnm.deepdive.teamassignments.service;

import edu.cnm.deepdive.teamassignments.model.dao.GroupRepository;
import edu.cnm.deepdive.teamassignments.model.dao.TaskRepository;
import edu.cnm.deepdive.teamassignments.model.dao.UserRepository;
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

  private final TaskRepository taskRepository;
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  /**
   * access task service repository.
   *
   * @param repository task repository
   * @param groupRepository  class
   * @param userRepository   class
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
   * @param task model entity
   * @param user verified at login
   * @return will save task to repository
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
   * @param taskId by long format
   * @return group repository tasks
   */
  public Optional<Task> get(long groupId, long taskId, User user) {   //TODO review with team
    return groupRepository
        .findById(groupId)
        .map((group) -> (group.getUsers().contains(user) || group.getOwner().equals(user))
            ? group
            : null
        )
        .flatMap((group) -> taskRepository.findById(taskId));
  }

  public Optional<Task> put(long groupId, long taskId, Task task,
      User user) {   //TODO review with team
    return groupRepository
        .findById(groupId)
        .map((group) -> group.getOwner().equals(user)
            ? group
            : null
        )
        .flatMap((group) -> taskRepository.findById(taskId))
        .map((savedTask) -> {
          savedTask.setTitle(task.getTitle());
          savedTask.setDescription(task.getDescription());
          savedTask.setDueDate(task.getDueDate());
          return taskRepository.save(savedTask);
        });
  }

  /**
   * Mark a task as completed.
   *
   * @param id of task
   * @param user is verified at login
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
   * @param taskId for selected task
   * @param user verifed at login
   */
  public Optional<Task> delete(long groupId, long taskId, User user) {
    return groupRepository
        .findById(groupId)
        .map((group) -> group.getOwner().equals(user)
            ? group
            : null
        )
        .flatMap((group) -> taskRepository.findById(taskId)
            .map((task) -> task.getGroup().equals(group) //TODO check other conditions for deletion
                ? task
                : null
            )
        )
        .map((task) -> {
          taskRepository.delete(task);
          return task;
        });
  }

  /**
   * To see if a task is assigned to group and member.
   * @param assigned boolean for is assigned
   * @param groupId for selected group
   * @param taskId for selected task
   * @param memberId that will be assigned task
   * @param user verified at log in
   * @return will return the group repository
   */
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

  /**
   * To see if a task is assigned to group and member.
   * @param groupId for selected group
   * @param taskId for selected task
   * @param memberId that will be assigned task
   * @param user verified at log in
   * @return will return the group repository
   */
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

  /**
   * This method will get a task by id.
   * @param groupId by long format
   * @param user required, will be verifed by log in
   * @return tasks in the choses group
   */
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
