import {BadRequestException, Injectable} from '@nestjs/common'
import * as common from "../common"
import {Content, FlexContent, Anchor} from "../entity"
import {FlexContentService} from "./flexContent.service"

export interface ContentMongo {
  id?: string
  elementId: string
  category?: string
  date: string
  data: Record<string, any>
  config?: Record<string, any>
}

@Injectable()
export class MongoService {
  constructor(private flexContentService: FlexContentService) {}

  /**
   * save content to mongoDB
   * @param type type collection name, also known as element type
   * @param content content to be saved
   * @returns the content w/ data as a pointer to actual data in mongoDB
   */
  async createContentToMongo(type: string, content: Content): Promise<Content> {
    if (content && content.data && content.element.id) {
      let flexContent = this.pgContentToFlexContent(content)
      // if content is new, create a new flexContent as well
      flexContent = await this.flexContentService.create(type, flexContent)
      // !IMPORTANT, replace content.data with pointer to flexContent
      content.data = {id: flexContent.id, collection: type}
      content.storageType = common.StorageType.MONGO
      return content
    }

    throw new BadRequestException("content is undefined")
  }

  async updateContentToMongo(type: string, fcId: string, content: Content): Promise<Content> {
    if (content && content.data && content.element.id) {
      let flexContent = this.pgContentToFlexContent(content)
      // if content already exists, update the flexContent (flexContent's ID is saved in content.data)
      flexContent = await this.flexContentService.update(type, fcId, flexContent)
      // !IMPORTANT, replace content.data with pointer to flexContent
      content.data = {id: flexContent.id, collection: type}
      content.storageType = common.StorageType.MONGO
      return content
    }

    throw new BadRequestException("content is undefined")
  }

  /**
   * convert the form of content to the request body datatype that could be processed by go-mongo-api
   * @param ct content to be converted
   * @returns converted content, the data will be sent to go-mongo-api
   */
  pgContentToFlexContent(ct: Content): FlexContent {
    const anchor = new Anchor(ct.category.name, ct.element.id, ct.id)
    const flexCt = new FlexContent(anchor, ct.date, ct.data, ct.config)
    return flexCt
  }

  /**
   * get flexContent from mongoDB
   * @param type collection name, also known as element type
   * @param id id of the flexContent
   * @returns FlexContent
   */
  async getContentData(type: string, id: string): Promise<FlexContent> {
    const res = await this.flexContentService.findOne(type, id)
    return res
  }
}
