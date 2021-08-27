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
   *
   * @param userService authenticates user
   * @param repository group repository
   */
  @Autowired
  public GroupService(UserService userService,
      GroupRepository repository) {
    this.userService = userService;
    this.repository = repository;
  }

  /**
   * Save a new group and set creator to group owner.
   *
   * @param group required to save
   * @param user authenticated
   * @return a saved group to repository
   */
  public final Group save(Group group, User user) {
    user = userService.get(user.getId()).orElseThrow();
    group.setOwner(user);
    group.getUsers().add(user);
    return repository.save(group);
  }

  /**
   * Gets groups owned by user
   * @param ownedOnly boolean, does belong to user
   * @param user autheticated
   * @return all groups by owner
   */
  public final Iterable<Group> getGroups(boolean ownedOnly, User user) {
    return ownedOnly
        ? repository.findAllByOwnerOrderByNameAsc(user)
        : repository.findAllByOwnerOrUsersContainsOrderByNameAsc(user, user);
  }

  /**
   * Basic group save method
   *
   * @param group from Group Class
   * @return saved group
   */
  public final Group save(Group group) {
    return repository.save(group);
  }

  /**
   * Allows group owners to add or remove members to or from an existing group.
   *
   * @param groupId is long
   * @param userId is long
   * @param putInGroup check true/false
   * @param owner authenticated at log in
   * @return add member to group
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
              } else if (!putInGroup && found) {
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
   *
   * @param groupId by long format
   * @param userId long format
   * @param requestor User, authenticated
   * @return repository for group
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
   *
   * @param id of group
   * @param user authenticated
   * @return return group if exist
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
   *
   * @param id of group
   * @param name of group
   * @param user verified at login
   * @return edited group
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
   *
   * @param id group id by long
   * @param user verified at log in
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
        .map((group) -> {
          group.getTasks().clear();
          group.getUsers().clear();
          return repository.save(group);
        })
        .ifPresent(repository::delete);

  }

  public Iterable<User> getMembers(long id, User user) {
    return repository
        .findById(id)
        .map((group) -> (group.getOwner().equals(user) || group.getUsers().contains(user))
            ? group
            : null
        )
        .map(Group::getUsers)
        .orElseThrow();
  }

}