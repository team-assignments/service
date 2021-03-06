package edu.cnm.deepdive.teamassignments.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 * Entity class for Group object using JPA mapping.
 */
@Entity
@Table(name = "group_team")
public class Group {

  /**
   * Long object used to identify the group.
   */
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "group_id",nullable = false,updatable = false)
  private Long id;

  /**
   * Foreign key identifying owner of group with user id.
   */
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "owner_id", nullable = false, updatable = false)
  private User owner;

  /**
   * Timestamp Date object verifying group creation date.
   */
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date creationDate;

  /**
   * String object providing group name.
   */
  @Column(nullable = false, updatable = true, unique = true)
  private String name;

  /**
   * Linked list of tasks for specified group.
   */
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy ("postDate DESC")
  @JsonIgnore
  private final List<Task> tasks = new LinkedList<>();

  /**
   * Many to many relationship mapping between Users and Groups.
   */
  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      name = "user_group_relationship",
      joinColumns = @JoinColumn(name = "group_id", nullable = false, updatable = false),
      inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false)
  )
  @OrderBy("displayName ASC")
  @JsonIgnore
  private final Set<User> users = new LinkedHashSet<>();


  /**
   * Gets the group's id
   * @return the group's id in long format
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the Date the group was created
   * @return the group's creation date
   */
  public Date getCreationDate() {
    return creationDate;
  }

  /**
   * Gets the owner of the group
   * @return the group owner
   */
  public User getOwner() {
    return owner;
  }

  /**
   * Sets teh owner of the group
   * @param owner of the group
   */
  public void setOwner(User owner) {
    this.owner = owner;
  }

  /**
   * Gets the name of user.
   * @return the name of user in string format
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of user
   * @param name of user in string format
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets a list of Tasks
   * @return list of task
   */
  @NonNull
  public List<Task> getTasks() {
    return tasks;
  }

  /**
   * Sets the user
   * @return the user
   */
  @NonNull
  public Set<User> getUsers() {
    return users;
  }

  /**
   * The hashcode to verify user
   * @return the hascode id
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  /**
   * Boolean status for the group class will be returned if it matches the request id.
   * @param obj the root of the class
   * @return the id of the group if it matches the request.
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
}
