package edu.cnm.deepdive.teamassignments.model.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;


@Entity
@Table(name = "group_team")
public class Group {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "group_id",nullable = false,updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "owner_id", nullable = false, updatable = false)
  private User owner;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date creationDate;

  @Column(nullable = false, updatable = true, unique = true)
  private String name;

  @NonNull
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
  @OrderBy ("postDate DESC")
  private final List<Task> tasks = new LinkedList<>();

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
  @OrderBy("displayName ASC")
  @NonNull
  private final List<User> users = new LinkedList<>();

  @Column(nullable = false, updatable = true)
  private boolean invited;

  public Long getId() {
    return id;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @NonNull
  public List<Task> getTasks() {
    return tasks;
  }

  @NonNull
  public List<User> getUsers() {
    return users;
  }

  public boolean isInvited() {
    return invited;
  }

  public void setInvited(boolean invited) {
    this.invited = invited;
  }
}
