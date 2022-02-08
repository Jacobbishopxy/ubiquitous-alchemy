/**
 * Created by Jacob Xie on 9/1/2020.
 */

import {
  Entity,
  PrimaryColumn,
  Column,
  ManyToMany,
  JoinTable,
  OneToMany
} from "typeorm"
import {Dashboard} from "."
import {author, RoleType} from "../common"
import {Record} from "./record.entity"


@Entity({name: author})
export class Author {

  @PrimaryColumn("varchar")
  email!: string

  @ManyToMany(() => Dashboard, t => t.authors, {nullable: true})
  @JoinTable()
  dashboards!: Dashboard[]

  @OneToMany(() => Record, r => r.author, {nullable: true})
  records!: Record[]

  @Column("varchar")
  nickname!: string

  @Column("varchar", {nullable: true})
  color!: string

  @Column("enum", {nullable: false, enum: RoleType})
  role!: RoleType

  @Column("text", {nullable: true})
  description?: string
}

