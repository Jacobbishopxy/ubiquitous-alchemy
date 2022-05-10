/**
 * Created by Jacob Xie on 1/20/2021.
 */

import {Body, Controller, Get, ParseArrayPipe, Post, Query} from '@nestjs/common'

import {RecordService} from "../provider"
import {OperationRecord} from "../entity"

@Controller()
export class RecordController {
  constructor(private service: RecordService) {}

  @Get("getRecords")
  getRecords(
    @Query("pagination", new ParseArrayPipe({
      optional: false,
      items: Number,
      separator: ","
    })) pagination: [number, number],
  ) {
    return this.service.getRecords(pagination)
  }

  @Post("saveLatestRecord")
  saveLatestRecord(@Body() record: OperationRecord) {
    return this.service.saveLatestRecord(record)
  }
}
