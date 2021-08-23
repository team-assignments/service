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
   * @return long id for task
   */
  public Long getId() {
    return id;
  }

  /**
   * getter for postDate.
   * @return post date task was created
   */
  public Date getPostDate() {
    return postDate;
  }

  /**
   * getter for dueDate.
   * @return due date of task
   */
  public Date getDueDate() {
    return dueDate;
  }

  /**
   * setter for dueDate.
   * @param dueDate for task
   */
  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * getter for user.
   * @return user
   */
  public User getUser() {
    return user;
  }

  /**
   * setter for user.
   * @param user of task
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * getter for group.
   * @return group
   */
  public Group getGroup() {
    return group;
  }

  /**
   * setter for group.
   * @param group is required
   */
  public void setGroup(Group group) {
    this.group = group;
  }

  /**
   * boolean for isCompleted.
   * @return boolean completed status
   */
  public boolean isCompleted() {
    return completed;
  }

  /**
   * boolean is completed.
   * @param completed boolean
   */
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  /**
   * boolean is confirmed complete.
   * @return confirmed complete status, ture or false
   */
  public boolean isConfirmedComplete() {
    return confirmedComplete;
  }

  /**
   * boolean set confirmed complete.
   * @param confirmedComplete boolean
   */
  public void setConfirmedComplete(boolean confirmedComplete) {
    this.confirmedComplete = confirmedComplete;
  }

  /**
   * Gets the title of the task
   * @return the title in String format
   */
  @NonNull
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the task
   * @param title of task in String format
   */
  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  /**
   * Gets the description of the task
   * @return description of task
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the task
   * @param description of task in String format
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * The hashcode to verify identity
   * @return id of hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  /**
   * Override .equals for User.
   * @param obj the root of the class
   * @return the task if it matches requested task
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
