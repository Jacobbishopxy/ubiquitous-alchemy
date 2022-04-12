/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth.domain;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "user_account")
public class UserAccount {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  private boolean active;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_account_role", joinColumns = @JoinColumn(name = "user_account_id"), inverseJoinColumns = @JoinColumn(name = "user_role_id"))
  private Collection<UserRole> roles;

  // =======================================================================
  // Constructors
  // =======================================================================

  public UserAccount() {
  }

  public UserAccount(Integer id) {
    this.id = id;
  }

  public UserAccount(String username, String email, boolean active, Collection<UserRole> roles) {
    this.username = username;
    this.email = email;
    this.active = active;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Collection<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Collection<UserRole> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("User [")
        .append("id =").append(id)
        .append(", username =").append(username)
        .append(", email =").append(email)
        .append(", active =").append(active)
        .append(", roles =").append(roles)
        .append("]");

    return builder.toString();
  }
}
