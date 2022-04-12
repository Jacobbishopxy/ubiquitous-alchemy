/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.domain;

import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_privilege")
public class UserPrivilege {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  private String description;

  @JsonIgnore
  @ManyToMany(mappedBy = "privileges")
  private Collection<UserRole> roles;

  // =======================================================================
  // Constructors
  // =======================================================================

  public UserPrivilege() {
  }

  public UserPrivilege(Integer id) {
    this.id = id;
  }

  public UserPrivilege(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public UserPrivilege(String name, String description, Collection<UserRole> roles) {
    this.name = name;
    this.description = description;
    this.roles = roles;
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

  public Collection<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Collection<UserRole> roles) {
    this.roles = roles;
  }

}
