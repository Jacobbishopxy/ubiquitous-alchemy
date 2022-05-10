/**
 * Created by Jacob Xie on 8/29/2020.
 */

import {
  Entity,
  Column,
  ManyToOne,
  ManyToMany,
  OneToMany,
  Unique,
  CreateDateColumn,
  UpdateDateColumn,
  PrimaryGeneratedColumn,
} from "typeorm"
import {Author, Category, Template, OperationRecord} from "."

import * as common from "../common"

@Entity({name: common.dashboard})
@Unique([common.name, common.category])
export class Dashboard {

  @PrimaryGeneratedColumn("uuid")
  id!: string

  @ManyToOne(() => Category, c => c.dashboards, {nullable: false})
  category!: Category

  @OneToMany(() => Template, t => t.dashboard, {nullable: true})
  templates!: Template[]

  @ManyToMany(() => Author, c => c.dashboards, {nullable: true, onDelete: "CASCADE"})
  authors!: Author[]

  @OneToMany(() => OperationRecord, r => r.dashboard, {nullable: true, cascade: true})
  records!: OperationRecord[]

  @Column("varchar")
  name!: string

  @Column("varchar", {nullable: true})
  symbol!: string

  @Column("text", {nullable: true})
  description?: string

  @CreateDateColumn({select: false})
  createdAt!: string

  @UpdateDateColumn({select: false})
  updatedAt!: string
}

