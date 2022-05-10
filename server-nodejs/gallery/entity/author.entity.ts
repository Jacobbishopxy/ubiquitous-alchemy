/**
 * Created by Jacob Xie on 9/1/2020.
 */

import {
  Entity,
  PrimaryColumn,
  Column,
  ManyToMany,
  JoinTable,
  OneToMany,
} from "typeorm"
import {Dashboard} from "."
import {author} from "../common"
import {OperationRecord} from "./operationRecord.entity"


@Entity({name: author})
export class Author {

  @PrimaryColumn("varchar")
  email!: string

  @Column("bool")
  active?: boolean

  @ManyToMany(() => Dashboard, t => t.authors, {nullable: true})
  @JoinTable()
  dashboards!: Dashboard[]

  @OneToMany(() => OperationRecord, r => r.author, {nullable: true, cascade: true})
  records!: OperationRecord[]

  @Column("varchar")
  nickname!: string

  @Column("varchar", {nullable: true})
  color!: string

  @Column("text", {nullable: true})
  description?: string
}

