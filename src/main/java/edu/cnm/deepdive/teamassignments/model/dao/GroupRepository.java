package edu.cnm.deepdive.teamassignments.model.dao;

import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

  List<Group> findAllByUsersContainingOrderByNameAsc(User user);

  List<Group> findAllByOwnerOrderByNameAsc(User owner);

 // List<Group> findAllByUsersContainingAndInvitedOrderByCreationDateAsc(User user, Group group);

 // List<Group> findAllByOwnerContainingAndInvitedOrderByIdAsc(User owner, boolean invited);


}