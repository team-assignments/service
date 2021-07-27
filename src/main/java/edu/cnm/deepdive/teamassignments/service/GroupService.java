package edu.cnm.deepdive.teamassignments.service;

import edu.cnm.deepdive.teamassignments.model.dao.GroupRepository;
import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  private final GroupRepository repository;

  @Autowired
  public GroupService(GroupRepository repository) {
    this.repository = repository;
  }

  List<Group> getIdGroupsByNameAsc(User user, Group group) {
    return repository.findAllByIdAndNameOrderByNameAsc(user, group);
  }

}
