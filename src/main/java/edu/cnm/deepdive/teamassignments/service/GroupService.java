package edu.cnm.deepdive.teamassignments.service;

import edu.cnm.deepdive.teamassignments.model.dao.GroupRepository;
import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class GroupService {

  private final GroupRepository repository;

  @Autowired
  public GroupService(GroupRepository repository) {
    this.repository = repository;
  }

  public Group save(Group group, User user) {
    group.setOwner(user);
    return repository.save(group);
  }

  public Optional<Group> get(long id, User user) {

    return repository.findById(id)
        .map((group) -> {
          if(group.getOwner().getId().equals(user.getId())) {
            return group;
          } else if (group.getUsers().contains(user)) {
            return group;
          } else {
            return null;
          }
        });

  }
  public Optional<Group> rename(long id, String name, User user) {

    return repository.findById(id)
        .map((group) -> {
          if(group.getOwner().getId().equals(user.getId())) {
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

  public void delete(long id, User user) {

    repository.findById(id)
        .map((group) -> {
          if(group.getOwner().getId().equals(user.getId())) {
            return group;
          } else {
            return null;
          }
        })
        .ifPresent(repository::delete);

  }

}
