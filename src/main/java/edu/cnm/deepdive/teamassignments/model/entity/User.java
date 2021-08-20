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
  @Column(nullable = false, updatable = false, unique = true)
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

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @OrderBy("dueDate desc")
  @NonNull
  private final List<Task> tasks = new LinkedList<>();


  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  @NonNull
  public Date getConnected() {
    return connected;
  }


  public void setConnected(@NonNull Date connected) {
    this.connected = connected;
  }


  public String getDisplayName() {
    return displayName;
  }


  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }


  public Date getCreationDate() {
    return creationDate;
  }


  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }


  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }


  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }


  @NonNull
  public List<Group> getGroups() {
    return groups;
  }


  @NonNull
  public List<Group> getOwnedGroups() {
    return ownedGroups;
  }


  @NonNull
  public List<Task> getTasks() {
    return tasks;
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
    } else if(obj instanceof User){
      User other = (User) obj;
      matches = (id != null && id.equals(other.id));
    }
    return matches;
  }
}
