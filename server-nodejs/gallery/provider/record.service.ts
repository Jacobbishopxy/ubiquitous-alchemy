/**
 * Created by Jacob Xie on 1/20/2021.
 */

import {Injectable} from "@nestjs/common"
import {InjectRepository} from "@nestjs/typeorm"
import {Repository} from "typeorm"

import * as common from "../common"
import * as utils from "../../utils"
import {Record} from "../entity"

const recordRelations = {
  relations: [
    common.author
  ]
}

@Injectable()
export class RecordService {
  constructor(
    @InjectRepository(Record, common.db)
    private repo: Repository<Record>,
  ) {}


  getRecords(pagination: [number, number]) {
    return this.repo.find({
      ...recordRelations,
      ...utils.paginationGet(pagination),
      ...utils.orderByCreatedAt("DESC")
    })
  }

  /**
   *  Check the latest record whose dashboardId, templateId, elementId
   *  is the same as the new record.
   *  If it does the same, then check the latest record's createdAt,
   *  and if their difference is less than 1 day, then delete the previous record.
   *
   * @param record `Record`
   * @returns `Record`
   */
  async saveLatestRecord(record: Record) {
    let newRecord = this.repo.create(record)
    newRecord = await this.repo.save(newRecord)

    let previousRecord = await this.repo.findOne({
      where: {
        dashboardId: record.dashboardId,
        templateId: record.templateId,
        elementId: record.elementId,
        ...utils.orderByCreatedAt("DESC")
      }
    })

    if (previousRecord) {
      const diff = utils.dateDiff(
        new Date(previousRecord.createdAt),
        new Date(newRecord.createdAt)
      )
      if (diff < 1) {
        await this.repo.delete(previousRecord.id)
      }
    }

    return newRecord
  }
}
