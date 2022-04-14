/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.domain;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  private String description;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role_privilege", joinColumns = @JoinColumn(name = "user_role_id"), inverseJoinColumns = @JoinColumn(name = "user_privilege_id"))
  private Collection<UserPrivilege> privileges;

  // =======================================================================
  // Constructors
  // =======================================================================

  public UserRole() {
  }

  public UserRole(Integer id) {
    this.id = id;
  }

  public UserRole(String name) {
    this.name = name;
  }

  public UserRole(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public UserRole(String name, Collection<UserPrivilege> privileges) {
    this.name = name;
    this.privileges = privileges;
  }

  public UserRole(String name, String description, Collection<UserPrivilege> privileges) {
    this.name = name;
    this.description = description;
    this.privileges = privileges;
  }

  // =======================================================================
  // Accessors
  // =======================================================================

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Collection<UserPrivilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Collection<UserPrivilege> privileges) {
    this.privileges = privileges;
  }

}
