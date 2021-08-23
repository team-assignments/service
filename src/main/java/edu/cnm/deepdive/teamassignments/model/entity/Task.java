package edu.cnm.deepdive.teamassignments.model.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

/**
 * Entity class for Group object using JPA mapping.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Task {


  /**
   * Long object used to identify the task.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "task_id", nullable = false, updatable = false)
  private Long id;


  /**
   * Timestamp Date object verifying group creation date.
   */
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date postDate;

  /**
   * Date object verifying group due date.
   */
  @Temporal(TemporalType.TIMESTAMP)
  private Date dueDate;

  /**
   * Foreign user id key used to identify whom the task is assigned to.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  /**
   * Foreign group object used to identify what group the task is associated with.
   */
  @NonNull
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "group_id", nullable = false, updatable = false)
  @JsonIgnore
  private Group group;

  /**
   * boolean value to determine if the task is complete.
   */
  @Column(nullable = false, updatable = true)
  private boolean completed;

  /**
   * boolean value to determine if the task was confirmed complete by the group owner.
   */
  @Column(nullable = false, updatable = true)
  private boolean confirmedComplete;

  /**
   * String object providing title of the task.
   */
  @NonNull
  @Column(nullable = false, length = 100)
  private String title;

  /**
   * String object providing additional information on task details.
   */
  @Column(length = 1024)
  private String description;

  /**
   * getter for id.
   * @return
   */
  public Long getId() {
    return id;
  }

  /**
   * getter for postDate.
   * @return
   */
  public Date getPostDate() {
    return postDate;
  }

  /**
   * getter for dueDate.
   * @return
   */
  public Date getDueDate() {
    return dueDate;
  }

  /**
   * setter for dueDate.
   * @param dueDate
   */
  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * getter for user.
   * @return
   */
  public User getUser() {
    return user;
  }

  /**
   * setter for user.
   * @param user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * getter for group.
   * @return
   */
  public Group getGroup() {
    return group;
  }

  /**
   * setter for group.
   * @param group
   */
  public void setGroup(Group group) {
    this.group = group;
  }

  /**
   * boolean for isCompleted.
   * @return
   */
  public boolean isCompleted() {
    return completed;
  }

  /**
   * boolean is completed.
   * @param completed
   */
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  /**
   * boolean is confirmed complete.
   * @return
   */
  public boolean isConfirmedComplete() {
    return confirmedComplete;
  }

  /**
   * boolean set confirmed complete.
   * @param confirmedComplete
   */
  public void setConfirmedComplete(boolean confirmedComplete) {
    this.confirmedComplete = confirmedComplete;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  /**
   * Override .equals for User.
   * @param obj
   * @return
   */
  @Override
  public boolean equals(Object obj) {
    boolean matches = false;
    if(this == obj) {
      matches = true;
    } else if(obj instanceof Task){
      Task other = (Task) obj;
      matches = (id != null && id.equals(other.id));
    }
    return matches;
  }

}
