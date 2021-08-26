package edu.cnm.deepdive.teamassignments.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

/**
 * User Entity Class.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    name = "user_profile"
)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id", nullable = false, updatable = false)
  private Long id;

  @NonNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date connected;

  @Column(nullable = false, unique = true)
  private String displayName;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date creationDate;

  @NonNull
  @JsonIgnore
  @Column(nullable = false, updatable = false, unique = true)
  @JsonIgnore
  private String oauthKey;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users") // added cascade
  @OrderBy("name ASC")
  @NonNull
  @JsonIgnore
  private final List<Group> groups = new LinkedList<>();


  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @OrderBy("name ASC")
  @NonNull
  @JsonIgnore
  private final List<Group> ownedGroups = new LinkedList<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @OrderBy("dueDate desc")
  @NonNull
  @JsonIgnore
  private final List<Task> tasks = new LinkedList<>();

  /**
   * gets the id of the task
   * @return long id of the task
   */
  public Long getId() {
    return id;
  }

  /**
   * sets teh id of the task
   * @param id of task in long
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get the date user connected to server
   * @return date user connected to server
   */
  @NonNull
  public Date getConnected() {
    return connected;
  }

  /**
   * Sets the Date user connected
   * @param connected is date user accessed server
   */
  public void setConnected(@NonNull Date connected) {
    this.connected = connected;
  }

  /**
   * Gets the display name of user
   * @return display name of user
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets the dispaly name of user
   * @param displayName of user
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Gets the Date item was created
   * @return the creation date
   */
  public Date getCreationDate() {
    return creationDate;
  }

  /**
   * Sets the Date created
   * @param creationDate for created item
   */
  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  /**
   * Gets users oauth key
   * @return users ouath key
   */
  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  /**
   * Sets the oauth key
   * @param oauthKey of user
   */
  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  /**
   * Gets a list of groups
   * @return groups
   */
  @NonNull
  public List<Group> getGroups() {
    return groups;
  }

  /**
   * Gets a list of Group
   * @return owned groups
   */
  @NonNull
  public List<Group> getOwnedGroups() {
    return ownedGroups;
  }

  /**
   * Gets a list of task
   * @return list of tasks
   */
  @NonNull
  public List<Task> getTasks() {
    return tasks;
  }

  /**
   * Gets the hash code needed to verify user
   * @return hash code id
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  /**
   * Override .equals for User.
   * @param obj root of class
   * @return the object if user matches stored id
   */
  @Override
  public boolean equals(Object obj) {
    boolean matches = false;
    if(this == obj) {
      matches = true;
    } else if(obj instanceof User){
      User other = (User) obj;
      matches = (id != null && id.equals(other.id));
    }
    return matches;
  }
}
