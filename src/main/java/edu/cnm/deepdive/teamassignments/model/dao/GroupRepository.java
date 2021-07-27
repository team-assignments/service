package edu.cnm.deepdive.teamassignments.model.dao;

import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

  List<Group> findAllByUsersContainingOrderByNameAsc(User user);

}
