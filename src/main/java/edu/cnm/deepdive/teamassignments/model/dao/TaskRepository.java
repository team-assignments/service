package edu.cnm.deepdive.teamassignments.model.dao;

import edu.cnm.deepdive.teamassignments.model.entity.Group;
import edu.cnm.deepdive.teamassignments.model.entity.Task;
import edu.cnm.deepdive.teamassignments.model.entity.User;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

  Iterable<Task> findAllByGroupAndCompletedAndDueDateBeforeOrderByDueDateAsc(Group group, boolean completed, Date cutoff);

  Iterable<Task> findAllByGroupAndCompletedAndDueDateAfterOrderByDueDateAsc(Group group, boolean completed, Date cutoff);

  Iterable<Task> findAllByGroupAndCompletedAndDueDateBetweenOrderByDueDateAsc(Group group, boolean completed, Date cutoffStart, Date cutoffEnd);

  Iterable<Task> findAllByUserAndCompletedAndDueDateBeforeOrderByDueDateAsc(User user, boolean completed, Date cutoff);

  Iterable<Task> findAllByUserAndCompletedAndDueDateAfterOrderByDueDateAsc(User user, boolean completed, Date cutoff);

  Iterable<Task> findAllByUserAndCompletedAndDueDateBetweenOrderByDueDateAsc(User user, boolean completed, Date cutoffStart, Date cutoffEnd);

}
