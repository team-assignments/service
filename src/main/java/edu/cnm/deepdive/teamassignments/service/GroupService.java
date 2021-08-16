package edu.cnm.deepdive.teamassignments.service;

import edu.cnm.deepdive.teamassignments.model.dao.GroupRepository;
import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Group Service class.
 */
@Service
public class GroupService {

  private final UserService userService;

  private final GroupRepository repository;

  /**
   * Constructor for Group service.
   * @param userService
   * @param repository
   */
  @Autowired
  public GroupService(UserService userService,
      GroupRepository repository) {
    this.userService = userService;
    this.repository = repository;
  }

  /**
   * Save a new group and set creator to group owner.
   * @param group
   * @param user
   * @return
   */
  public final Group save(Group group, User user) {
    user = userService.get(user.getId()).orElseThrow();
    group.setOwner(user);
    group.getUsers().add(user);
    return repository.save(group);
  }

  public final List<Group> getGroups(){
    return repository.findAllOrderByNameAsc();
  }

  /**
   * Basic group save method
   * @param group
   * @return
   */
  public final Group save(Group group) {
    return repository.save(group);
  }

  /**
   * Allows group owners to add or remove members to or from an existing group.
   * @param groupId
   * @param userId
   * @param putInGroup
   * @param owner
   * @return
   */
  public Boolean toggleMembership(long groupId, long userId, boolean putInGroup, User owner) {
    return repository
        .findById(groupId)
        .map((group) -> group.getOwner().getId().equals(owner.getId()) ? group : null)
        .map((group) -> userService
            .get(userId)
            .map((user) -> {
              Set<User> users = group.getUsers();
              boolean found = users.contains(user);
              if (putInGroup && !found) {
                users.add(user);
              } else if(!putInGroup && found) {
                group.getUsers().remove(user);
              }
              return group;
            })
            .orElseThrow())
        .map((group) -> {
          repository.save(group);
          return putInGroup;
        })
        .orElseThrow();
  }

  /**
   * Check to see if a user is a member of a group.
   * @param groupId
   * @param userId
   * @param requestor
   * @return
   */
  public boolean checkMembership(long groupId, long userId, User requestor) {
    return repository
        .findById(groupId)
        .map((group) -> (group.getOwner().equals(requestor) || group.getUsers().contains(requestor))
            ? group
            : null)
        .map((group) -> userService
            .get(userId)
            .map((user) -> group.getUsers().contains(user))
            .orElseThrow())
        .orElseThrow();
  }

  /**
   * Gets all members of a group.
   * @param id
   * @param user
   * @return
   */
  public Optional<Group> get(long id, User user) {

    return repository.findById(id)
        .map((group) -> {
          if (group.getOwner().getId().equals(user.getId())) {
            return group;
          } else if (group.getUsers().contains(user)) {
            return group;
          } else {
            return null;
          }
        });

  }

  /**
   * Allows a group to be renamed.
   * @param id
   * @param name
   * @param user
   * @return
   */
  public Optional<Group> rename(long id, String name, User user) {

    return repository.findById(id)
        .map((group) -> {
          if (group.getOwner().getId().equals(user.getId())) {
            return group;
          } else {
            return null;
          }
        })
        .map((group) -> {
          group.setName(name);
          return repository.save(group);
        });

  }

  /**
   * Allows a group to be deleted.
   * @param id
   * @param user
   */
  public void delete(long id, User user) {

    repository.findById(id)
        .map((group) -> {
          if (group.getOwner().getId().equals(user.getId())) {
            return group;
          } else {
            return null;
          }
        })
        .ifPresent(repository::delete);

  }

}