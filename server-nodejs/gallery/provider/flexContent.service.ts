/**
 * Created by Jacob Xie on 2/9/2021.
 */

import {BadRequestException, Injectable, NotFoundException} from "@nestjs/common"
import {InjectRepository} from "@nestjs/typeorm"
import {FlexContent} from "../entity"
import {DeleteResult, MongoRepository, ObjectID, UpdateResult} from "typeorm"

import * as common from "../common"

@Injectable()
export class FlexContentService {
  constructor(
    @InjectRepository(FlexContent, common.dbExt)
    private repo: MongoRepository<FlexContent>
  ) {}

  findAll(): Promise<FlexContent[]> {
    return this.repo.find()
  }

  async findOne(id: string): Promise<FlexContent> {
    // validate id
    if (!ObjectID.isValid(id)) throw new BadRequestException("Invalid id")
    // find one
    const fc = await this.repo.findOne(id)
    if (!fc) throw new NotFoundException("FlexContent not found")
    return fc
  }

  create(content: FlexContent): Promise<FlexContent> {
    // remove id
    content.id = undefined
    const create = this.repo.create(content)
    return this.repo.save(create)
  }

  async update(id: string, content: FlexContent): Promise<UpdateResult> {
    // validate id
    if (!ObjectID.isValid(id)) throw new BadRequestException("Invalid id")
    // check existence
    const exists = await this.repo.findOne(id)
    if (!exists) throw new NotFoundException("FlexContent not found")
    // update by id
    const update = await this.repo.update(content.id!, content)
    return update
  }

  async delete(id: string): Promise<DeleteResult> {
    // turn string into ObjectID
    if (!ObjectID.isValid(id)) throw new BadRequestException("Invalid id")
    return await this.repo.delete(id)
  }
}
