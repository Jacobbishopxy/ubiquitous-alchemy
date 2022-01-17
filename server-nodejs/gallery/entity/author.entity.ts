/**
 * Created by Jacob Xie on 9/1/2020.
 */

import {
  Entity,
  PrimaryColumn,
  Column,
  ManyToMany,
  JoinTable
} from "typeorm"
import {Dashboard} from "."
import {author, RoleType} from "../common"


@Entity({name: author})
export class Author {

  @PrimaryColumn("varchar")
  email!: string

  @ManyToMany(() => Dashboard, t => t.authors, {nullable: true})
  @JoinTable()
  dashboards!: Dashboard[]

  @Column("varchar")
  nickname!: string

  @Column("enum", {nullable: false, enum: RoleType})
  role!: RoleType

  @Column("text", {nullable: true})
  description?: string
}

