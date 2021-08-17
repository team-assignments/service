package edu.cnm.deepdive.teamassignments.model.entity;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
import org.hibernate.annotations.CreationTimestamp;
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


  @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
  @OrderBy ("postDate DESC")
  private final List<Task> tasks = new LinkedList<>();

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      name = "user_group_relationship",
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

  /**
   * getter for id.
   * @return
   */
  public Long getId() {
    return id;
  }

  /**
   * getter for creationDate.
   * @return
   */
  public Date getCreationDate() {
    return creationDate;
  }

  /**
   * getter for owner.
   * @return
   */
  public User getOwner() {
    return owner;
  }

  /**
   * setter for owner.
   * @param owner
   */
  public void setOwner(User owner) {
    this.owner = owner;
  }

  /**
   * getter for name.
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * setter for name.
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * getter for list tasks.
   * @return
   */
  @NonNull
  public List<Task> getTasks() {
    return tasks;
  }

  /**
   * getter for users.
   * @return
   */
  @NonNull
  public Set<User> getUsers() {
    return users;
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
    } else if(obj instanceof Group){
      Group other = (Group) obj;
      matches = (id != null && id.equals(other.id));
    }
    return matches;
  }



//  @Nullable
//  public List<User> getInvitedUsers() {
//    return invitedUsers;
//  }
}
