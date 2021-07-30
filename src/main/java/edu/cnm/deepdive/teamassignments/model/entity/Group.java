package edu.cnm.deepdive.teamassignments.model.entity;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Null;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


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


  @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
  @OrderBy ("postDate DESC")
  private final List<Task> tasks = new LinkedList<>();

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(
      name = "user_group",
      joinColumns = @JoinColumn(name = "group_id", nullable = false, updatable = false),
      inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false)
  )
  @OrderBy("displayName ASC")
  private final Set<User> users = new LinkedHashSet<>();


// // @Column(nullable = false, updatable = true)
//  @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
//  @OrderBy("displayName ASC")
//  @Nullable
//  private final List<User> invitedUsers = new LinkedList<>();

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
  public Set<User> getUsers() {
    return users;
  }



//  @Nullable
//  public List<User> getInvitedUsers() {
//    return invitedUsers;
//  }
}
