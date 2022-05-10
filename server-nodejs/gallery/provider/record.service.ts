/**
 * Created by Jacob Xie on 1/20/2021.
 */

import {Injectable} from "@nestjs/common"
import {InjectRepository} from "@nestjs/typeorm"
import {Repository} from "typeorm"

import * as common from "../common"
import * as utils from "../../utils"
import {OperationRecord} from "../entity"

const recordRelations = {
  relations: [
    common.author,
    common.dashboard,
    common.dashboardCategory,
    common.template,
    common.element,
  ]
}

@Injectable()
export class RecordService {
  constructor(
    @InjectRepository(OperationRecord, common.db)
    private repo: Repository<OperationRecord>,
  ) {}


  getRecords(pagination: [number, number]) {
    return this.repo.find({
      ...recordRelations,
      ...utils.paginationGet(pagination),
      ...utils.orderByCreatedAt("DESC"),
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
  async saveLatestRecord(record: OperationRecord) {
    let newRecord = this.repo.create(record)
    newRecord = await this.repo.save(newRecord)

    let previousRecord = await this.repo.findOne({
      ...recordRelations,
      where: {
        dashboard: {id: record.dashboard.id},
        template: {id: record.template.id},
        element: {id: record.element.id},
      },
      ...utils.orderByCreatedAt("ASC")
    })

    if (previousRecord && newRecord.id != previousRecord.id) {
      const diff = utils.dateDiff(
        new Date(previousRecord.createdAt),
        new Date(newRecord.createdAt),
      )

      if (diff < 1) {
        await this.repo.delete(previousRecord.id)
      }
    }

    return newRecord
  }
}
