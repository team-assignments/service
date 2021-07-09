package edu.cnm.deepdive.teamassignmentsservice.model.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;


@Entity
public class Group {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "group_id",nullable = false,updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "owner_id", nullable = false, updatable = false)
  private User owner;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date creationDate;

  @Column(nullable = false, updatable = true, unique = true)
  private String name;

  @OneToMany(fetch = FetchType.LAZY,
      mappedBy = "group",
      cascade = CascadeType.ALL)
  @OrderBy("postDate DESC")
  private List<Task> tasks = new LinkedList<>();

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
}
