package edu.cnm.deepdive.teamassignments.model.entity;



import java.util.Date;
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

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "task_id", nullable = false, updatable = false)
  private Long id;


  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date postDate;


  @Temporal(TemporalType.TIMESTAMP)
  private Date dueDate;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "user_id")    //investigate if assignment will happen immediately on creation, if so nullable = false. If chagne user updateable = false.
  private User user;


  @NonNull
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "group_id", nullable = false, updatable = false)
  private Group group;

  @Column(nullable = false, updatable = true)
  private boolean completed;

  @Column(nullable = false, updatable = true)
  private boolean confirmedComplete;

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
   * @param groupId
   */
  public void setGroup(Group groupId) {
    this.group = groupId;
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
}
