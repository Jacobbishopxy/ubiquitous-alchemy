/**
 * Created by Jacob Xie on 8/29/2020.
 */

import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  Unique,
  ManyToOne,
  ManyToMany,
  UpdateDateColumn,
  CreateDateColumn,
} from "typeorm"

import * as common from "../common"
import {Element} from "./element.entity"
import {Category} from "./category.entity"
import {Tag} from "./tag.entity"
import {Mark} from "./mark.entity"

@Entity({name: common.content})
@Unique([common.category, common.element, common.date])
export class Content {

  @PrimaryGeneratedColumn("uuid")
  id!: string

  /**
   * if an element is deleted, its' content would not be destroyed but unbinding from element
   */
  @ManyToOne(() => Element, e => e.contents, {nullable: true, onDelete: "SET NULL"})
  element!: Element

  @ManyToOne(() => Category, c => c.contents, {nullable: true, onDelete: "SET NULL"})
  category!: Category

  @ManyToOne(() => Mark, m => m.contents, {cascade: true, nullable: true})
  mark!: Mark

  @ManyToMany(() => Tag, t => t.contents, {cascade: true, nullable: true})
  tags!: Tag[]

  @Column("timestamp", {nullable: false})
  date!: string

  @Column("text", {nullable: true})
  title!: string

  @Column("json", {nullable: false})
  data!: Record<string, any>

  @Column("json", {nullable: true})
  config?: Record<string, any>

  @Column("varchar", {nullable: true})
  storageType?: string

  @CreateDateColumn({nullable: true})
  createdAt!: string

  @UpdateDateColumn({nullable: true})
  updatedAt!: string
}

