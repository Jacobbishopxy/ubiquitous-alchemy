/**
 * Created by Jacob Xie on 9/14/2020.
 */

import {Body, Controller, Delete, Get, Post, Query} from '@nestjs/common'

import {CategoryService} from "../provider"
import {Category, Mark, Tag} from "../entity"
import {CategoryPureDto} from "../dto"
import {CategoryPurePipe} from "../pipe"


@Controller()
export class CategoryController {
  constructor(private readonly service: CategoryService) {}

  @Get("categories")
  getAllCategories() {
    return this.service.getAllCategories()
  }

  @Get("category")
  getCategoryByName(@Query("name") name: string) {
    return this.service.getCategoryByName(name)
  }

  @Post("category")
  saveCategory(@Body() category: Category) {
    return this.service.saveCategory(category)
  }

  @Delete("category")
  deleteCategory(@Query("name") name: string) {
    return this.service.deleteCategory(name)
  }

  // ===================================================================================================================

  @Get("getAllCategoriesByType")
  getAllCategoriesByType(@Query("type") type: string) {
    return this.service.getAllCategoriesByType(type)
  }

  @Get("getAllCategories")
  getAllCategoriesName() {
    return this.service.getAllCategoriesNameWithType()
  }

  @Get("getAllCategoriesWithoutContents")
  getAllCategoriesWithoutContents() {
    return this.service.getAllCategoriesWithoutContents()
  }

  @Get("getAllCategoriesDashboardsTemplates")
  getAllCategoriesDashboardsTemplates() {
    return this.service.getAllCategoriesDashboardsTemplates()
  }

  @Get("getCategoryMarkAndTagByName")
  getCategoryMarkAndTagByName(@Query("name") name: string) {
    return this.service.getCategoryMarkAndTagByName(name)
  }

  @Get("getCategoryContentByName")
  getCategoryContentByName(@Query("name") name: string) {
    return this.service.getCategoryContentByName(name)
  }

  @Get("getCategoryDashboardByName")
  getCategoryDashboardByName(@Query("name") name: string) {
    return this.service.getCategoryDashboardByName(name)
  }

  @Post("saveCategory")
  savePureCategory(@Body(CategoryPurePipe) category: CategoryPureDto) {
    return this.service.saveCategory(category as Category)
  }

  @Post("saveCategoryMark")
  saveCategoryMark(
    @Query("name") name: string,
    @Body() mark: Mark
  ) {
    return this.service.saveCategoryMark(name, mark)
  }

  @Post("saveCategoryTag")
  saveCategoryTag(
    @Query("name") name: string,
    @Body() tag: Tag
  ) {
    return this.service.saveCategoryTag(name, tag)
  }
}

