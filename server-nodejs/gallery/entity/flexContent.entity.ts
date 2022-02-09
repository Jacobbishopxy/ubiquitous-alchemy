/**
 * Created by Jacob Xie on 2/9/2021.
 */

import {Entity, ObjectID, ObjectIdColumn, Column} from "typeorm"


export class Anchor {

  @Column()
  categoryName!: string

  @Column()
  dashboardId!: string

  @Column()
  templateId!: string

  @Column()
  elementId!: string

  @Column()
  contentId!: string

  constructor(categoryName: string, dashboardId: string, templateId: string, elementId: string, contentId: string) {
    this.categoryName = categoryName
    this.dashboardId = dashboardId
    this.templateId = templateId
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
  config!: Record<string, any>
}
