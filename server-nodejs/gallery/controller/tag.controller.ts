/**
 * Created by Jacob Xie on 9/8/2020.
 */

import {Body, Controller, Delete, Get, Post, Query} from '@nestjs/common'

import {TagService} from "../provider"
import {Tag} from "../entity"
import {ParseArray} from "../pipe"


@Controller()
export class TagController {
  constructor(private service: TagService) {}

  @Get("tags")
  getAllTags() {
    return this.service.getAllTags()
  }

  @Get("tag")
  getTagsByName(@Query("name") name: string) {
    return this.service.getTagsByName(name)
  }

  @Post("tag")
  saveTag(@Body() tag: Tag) {
    return this.service.saveTag(tag)
  }

  @Delete("tag")
  deleteTag(@Query("id") id: string) {
    return this.service.deleteTag(id)
  }

  // ===================================================================================================================

  @Get("getCategoriesByTagName")
  getCategoriesByTagName(@Query("name") name: string) {
    return this.service.getCategoriesByTagName(name)
  }

  @Post("modifyTag")
  modifyTag(@Body() tag: Tag) {
    return this.service.modifyTag(tag)
  }

  @Delete("deleteTagInCategory")
  deleteTagInCategory(
    @Query("categoryName") categoryName: string,
    @Query("tagName") tagName: string,
  ) {
    return this.service.deleteTagInCategory(categoryName, tagName)
  }

  @Post("saveTags")
  saveTags(@Body() tags: Tag[]) {
    return this.service.saveTags(tags)
  }

  @Delete("deleteTags")
  deleteTags(@Query("ids", new ParseArray({type: String, separator: ","})) ids: string[]) {
    return this.service.deleteTags(ids)
  }

  @Post("updateTagsInCategory")
  updateTagsInCategory(
    @Query("categoryName") categoryName: string,
    @Body() tags: Tag[],
  ) {
    return this.service.updateTagsInCategory(categoryName, tags)
  }
}

