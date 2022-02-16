/**
 * Created by Jacob Xie on 2/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
@TypeDef(name = "author_role_enum", typeClass = RolePostgresEnumType.class)
public class Author {
  @Id
  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String nickname;

  private String color;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Type(type = "author_role_enum")
  private Role role;

  private String description;

  public Author() {
  }

  public Author(String email, String nickname, String color, Role role, String description) {
    this.email = email;
    this.nickname = nickname;
    this.color = color;
    this.role = role;
    this.description = description;
  }

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
