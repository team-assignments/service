package edu.cnm.deepdive.teamassignmentsservice.model.entity;


import edu.cnm.deepdive.teamassignmentsservice.model.entity.User.Role;
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
  @JoinColumn(name = "user_id")
  private User user;


  @Enumerated(EnumType.ORDINAL)
  private Role role;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "group_id", nullable = false, updatable = false)
  private Group group;

  public Long getId() {
    return id;
  }

  public Date getPostDate() {
    return postDate;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group groupId) {
    this.group = groupId;
  }
}
