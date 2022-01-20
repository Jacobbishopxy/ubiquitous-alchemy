/**
 * Created by Jacob Xie on 1/20/2021.
 */

import {
  Entity,
  Column,
  PrimaryGeneratedColumn,
  ManyToOne,
  CreateDateColumn,
  Index
} from "typeorm"

import {record} from "../common"
import {Author} from "./author.entity"

@Entity({name: record})
export class Record {

  @PrimaryGeneratedColumn("uuid")
  id!: string

  @ManyToOne(() => Author, a => a.records, {nullable: false})
  author!: Author

  @Column("varchar", {nullable: false})
  dashboardId!: string

  @Column("varchar", {nullable: false})
  templateId!: string

  @Column("varchar", {nullable: false})
  elementId!: string

  @Column("text", {nullable: true})
  note?: string

  @CreateDateColumn()
  @Index()
  createdAt!: string
}

