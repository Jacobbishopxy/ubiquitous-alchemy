/**
 * Created by Jacob Xie on 9/8/2020.
 */

import {Body, Controller, Delete, Get, Post, Query} from '@nestjs/common'

import {MarkService} from "../provider"
import {Mark} from "../entity"
import {ParseArray} from "../pipe"


@Controller()
export class MarkController {
  constructor(private readonly service: MarkService) {}

  @Get("marks")
  getAllMarks() {
    return this.service.getAllMarks()
  }

  @Get("mark")
  getMarksByName(@Query("name") name: string) {
    return this.service.getMarksByName(name)
  }

  @Post("mark")
  saveMark(@Body() mark: Mark) {
    return this.service.saveMark(mark)
  }

  @Delete("mark")
  deleteMark(@Query("id") id: string) {
    return this.service.deleteMark(id)
  }

  // ===================================================================================================================

  @Get("getCategoriesByMarkName")
  getCategoriesByMarkName(@Query("name") name: string) {
    return this.service.getCategoriesByMarkName(name)
  }

  @Post("modifyMark")
  modifyMark(@Body() mark: Mark) {
    return this.service.modifyMark(mark)
  }

  @Delete("deleteMarkInCategory")
  deleteMarkInCategory(
    @Query("categoryName") categoryName: string,
    @Query("markName") markName: string,
  ) {
    return this.service.deleteMarkInCategory(categoryName, markName)
  }

  @Post("saveMarks")
  saveMarks(@Body() marks: Mark[]) {
    return this.service.saveMarks(marks)
  }

  @Delete("deleteMarks")
  deleteMarks(
    @Query("ids", new ParseArray({type: String, separator: ","}))
    ids: string[]
  ) {
    return this.service.deleteMarks(ids)
  }

  @Post("updateMarksInCategory")
  updateMarksInCategory(
    @Query("categoryName") categoryName: string,
    @Body() marks: Mark[],
  ) {
    return this.service.updateMarksInCategory(categoryName, marks)
  }
}

