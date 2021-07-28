package edu.cnm.deepdive.teamassignments.model.dao;

import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findFirstByOauthKey(String oauthKey);

  @Query("select distinct "
      + "u from User as u "
      + "inner join Group as g "
      + "inner join Task as t on t.user = u and t.group = g "
      + "where g = :group and t.completed = :completed and t.dueDate < :cutoff "
      + "order by u.displayName")
  Iterable<User> findAllByGroupsContainingAndTasksNotCompletedAndOverdue(Group group, boolean completed, Date cutoff);

  Iterable<User> findAllByGroupsNotEmptyOrderByDisplayNameAsc();

  @Query("select distinct "
      + "u from User as u "
      + "inner join Group as g "
      + "where g.owner = :owner "
      + "order by u.displayName")
  Iterable<User> findAllUsersInMyOwnGroups(User owner);

  @Query("select distinct "
      + "u1 from User as u1 "
      + "inner join Group as g "
      + "inner join User as u2 "
      + "where u1 <> u2 "
      + "and u1 = :user "
      + "order by u1.displayName")
  Iterable<User> findAllUserInMyGroups(User user);
}
