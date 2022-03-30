/**
 * Created by Jacob Xie on 2/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.utility.domain;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.domain.obj.Role;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.domain.obj.RolePostgresEnumType;

/**
 * Promoter
 *
 * A promoter is a person who promotes an asset.
 * This table has been already created by other program (server-nodejs).
 */
@Entity
@Table(name = "author")
@TypeDef(name = "author_role_enum", typeClass = RolePostgresEnumType.class)
public class Promoter {
  // =======================================================================
  // Fields
  // =======================================================================

  @Id
  @Column(nullable = false)
  @Email
  @NotEmpty
  @Schema(description = "The email of the promoter.", example = "jacob@example.com", required = true)
  private String email;

  @Column(nullable = false)
  @NotEmpty
  private String nickname;

  private String color;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Type(type = "author_role_enum")
  @NotEmpty
  @Schema(description = "The role of the promoter.", allowableValues = { "visitor", "editor", "admin",
      "supervisor" }, required = true)
  private Role role;

  @Column(columnDefinition = "TEXT")
  private String description;

  // =======================================================================
  // Constructors
  // =======================================================================

  public Promoter() {
  }

  public Promoter(String email) {
    super();
    this.email = email;
  }

  public Promoter(String email, String nickname, String color, Role role, String description) {
    super();
    this.email = email;
    this.nickname = nickname;
    this.color = color;
    this.role = role;
    this.description = description;
  }

  // =======================================================================
  // Accessors
  // =======================================================================

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
