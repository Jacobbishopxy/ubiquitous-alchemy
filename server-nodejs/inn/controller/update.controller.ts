/**
 * Created by Jacob Xie on 2/3/2021
 */

import {Body, Controller, Delete, Get, ParseArrayPipe, Post, Query} from '@nestjs/common'

import {Update} from "../entity"
import {UpdateService} from "../provider"


@Controller()
export class UpdateController {
  constructor(private readonly service: UpdateService) {}

  @Get("updates")
  getAllUpdate() {
    return this.service.getAllUpdate()
  }

  @Get("update")
  getUpdateById(@Query("id") id: string) {
    return this.service.getUpdateById(id)
  }

  @Post("update")
  saveUpdate(@Body() update: Update) {
    return this.service.saveUpdate(update)
  }

  @Delete("update")
  deleteUpdate(@Query("id") id: string) {
    return this.service.deleteUpdate(id)
  }

  @Get("getLatestUpdate")
  getLatestUpdate(
    @Query("pagination", new ParseArrayPipe({
      optional: true,
      items: Number,
      separator: ","
    })) pagination?: [number, number],
  ) {
    return this.service.getLatestUpdate(pagination)
  }

  @Get("getUpdateCount")
  getUpdateCount() {
    return this.service.getUpdateCount()
  }
}

