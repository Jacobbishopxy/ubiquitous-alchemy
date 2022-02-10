/**
 * Created by Jacob Xie on 2/9/2021.
 */

import {Body, Controller, Delete, Get, Param, Post, Put, Query} from "@nestjs/common"
import {FlexContent} from "../entity"
import {FlexContentService} from "../provider"

@Controller()
export class FlexContentController {
  constructor(private service: FlexContentService) {}

  @Get("flexContents")
  getAllFlexContents(@Query('collection') collection: string) {
    return this.service.findAll(collection)
  }

  @Get("flexContent/:id")
  getFlexContent(
    @Param('id') id: string,
    @Query('collection') collection: string,
  ) {
    return this.service.findOne(collection, id)
  }

  @Post("flexContent")
  createFlexContent(
    @Query('collection') collection: string,
    @Body() fc: FlexContent,
  ) {
    return this.service.create(collection, fc)
  }

  @Put("flexContent/:id")
  updateFlexContent(
    @Query('collection') collection: string,
    @Param('id') id: string,
    @Body() fc: FlexContent,
  ) {
    return this.service.update(collection, id, fc)
  }

  @Delete("flexContent/:id")
  deleteFlexContent(
    @Query('collection') collection: string,
    @Param('id') id: string,
  ) {
    return this.service.delete(collection, id)
  }

}
