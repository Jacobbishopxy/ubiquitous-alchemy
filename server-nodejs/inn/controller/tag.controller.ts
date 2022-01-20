/**
 * Created by Jacob Xie on 2/4/2021
 */

import {Body, Controller, Delete, Get, Post, Query} from '@nestjs/common'

import {Tag} from "../entity"
import {TagService} from "../provider"


@Controller()
export class TagController {
  constructor(private readonly service: TagService) {}

  @Get("tags")
  getAllTags() {
    return this.service.getAllTags()
  }

  @Get("tag")
  getTagById(@Query("id") id: string) {
    return this.service.getTagById(id)
  }

  @Post("tag")
  saveTag(@Body() tag: Tag) {
    return this.service.saveTag(tag)
  }

  @Delete("tag")
  deleteTag(@Query("id") id: string) {
    return this.service.deleteTag(id)
  }

  @Post("modifyTags")
  modifyTags(@Body() tags: Tag[]) {
    return this.service.modifyTags(tags)
  }
}

