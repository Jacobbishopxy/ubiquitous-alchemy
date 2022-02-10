/**
 * Created by Jacob Xie on 2/9/2021.
 */

import {Entity, ObjectID, ObjectIdColumn, Column} from "typeorm"


export class Anchor {

  @Column()
  categoryName!: string

  @Column()
  elementId!: string

  @Column()
  contentId?: string

  constructor(categoryName: string, elementId: string, contentId?: string) {
    this.categoryName = categoryName
    this.elementId = elementId
    this.contentId = contentId
  }
}


@Entity()
export class FlexContent {

  @ObjectIdColumn()
  id?: ObjectID

  @Column(() => Anchor)
  anchor?: Anchor

  @Column()
  date!: string

  @Column()
  data!: Record<string, any>

  @Column()
  config?: Record<string, any>

  constructor(anchor: Anchor, date: string, data: Record<string, any>, config?: Record<string, any>) {
    this.anchor = anchor
    this.date = date
    this.data = data
    this.config = config
  }
}
