/**
 * Created by Jacob Xie on 2/9/2021.
 */

import {Body, Controller, Delete, Get, Param, Post, Put} from "@nestjs/common"
import {FlexContent} from "../entity"
import {FlexContentService} from "../provider"

@Controller()
export class FlexContentController {
  constructor(private service: FlexContentService) {}

  @Get("flexContents")
  getAllFlexContents() {
    return this.service.findAll()
  }

  @Get("flexContent/:id")
  getFlexContent(@Param('id') id: string) {
    return this.service.findOne(id)
  }

  @Post("flexContent")
  createFlexContent(@Body() fc: FlexContent) {
    return this.service.create(fc)
  }

  @Put("flexContent/:id")
  updateFlexContent(@Param('id') id: string, @Body() fc: FlexContent) {
    return this.service.update(id, fc)
  }

  @Delete("flexContent/:id")
  deleteFlexContent(@Param('id') id: string) {
    return this.service.delete(id)
  }
}