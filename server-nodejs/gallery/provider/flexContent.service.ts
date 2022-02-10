/**
 * Created by Jacob Xie on 2/9/2021.
 */

import {BadRequestException, Injectable, NotFoundException} from "@nestjs/common"
import {InjectRepository} from "@nestjs/typeorm"
import {FlexContent} from "../entity"
import {DeleteResult, MongoRepository} from "typeorm"
import {ObjectId} from "mongodb"

import * as common from "../common"

@Injectable()
export class FlexContentService {
  constructor(
    @InjectRepository(FlexContent, common.dbExt)
    private repo: MongoRepository<FlexContent>
  ) {}

  // switch collection
  private switchCollection(collection: string): void {
    this.repo.metadata.tableName = collection
  }

  findAll(collection: string): Promise<FlexContent[]> {
    this.switchCollection(collection)
    return this.repo.find()
  }

  async findOne(collection: string, id: string): Promise<FlexContent> {
    this.switchCollection(collection)
    // validate id
    if (!ObjectId.isValid(id)) throw new BadRequestException("Invalid id")
    // find one
    const fc = await this.repo.findOne(id)
    if (!fc) throw new NotFoundException("FlexContent not found")
    return fc
  }

  create(collection: string, content: FlexContent): Promise<FlexContent> {
    this.switchCollection(collection)
    // remove id
    content.id = undefined
    const create = this.repo.create(content)
    return this.repo.save(create)
  }

  async update(collection: string, id: string, content: FlexContent): Promise<FlexContent> {
    this.switchCollection(collection)
    // validate id
    if (!ObjectId.isValid(id)) throw new BadRequestException("Invalid id")
    // check existence
    const exists = await this.repo.findOne(id)
    if (!exists) throw new NotFoundException("FlexContent not found")
    // update by id
    await this.repo.update(content.id!, content)
    const update = await this.repo.findOne(id)
    return update!
  }

  async delete(collection: string, id: string): Promise<DeleteResult> {
    this.switchCollection(collection)
    // turn string into ObjectID
    if (!ObjectId.isValid(id)) throw new BadRequestException("Invalid id")
    return await this.repo.delete(id)
  }
}
