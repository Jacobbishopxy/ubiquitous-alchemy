/**
 * Created by Jacob Xie on 8/29/2020.
 */

import {Body, Controller, Delete, Get, Post, Query} from '@nestjs/common'

import {TemplateService} from "../provider"
import {Template} from "../entity"
import {TemplateCopyElementsDto} from "../dto"
import {TemplateCopyElementsPipe, ParseArray} from "../pipe"


@Controller()
export class TemplateController {
  constructor(private service: TemplateService) {}

  @Get("templates")
  getAllTemplates() {
    return this.service.getAllTemplates()
  }

  @Get("template")
  getTemplateById(@Query("id") id: string) {
    return this.service.getTemplateById(id)
  }

  @Post("template")
  saveTemplate(@Body() template: Template) {
    return this.service.saveTemplate(template)
  }

  @Delete("template")
  deleteTemplate(@Query("id") id: string) {
    return this.service.deleteTemplate(id)
  }

  // ===================================================================================================================

  @Get("getTemplateElementsContents")
  getTemplateElementsContents(@Query("id") id: string) {
    return this.service.getTemplateElementsContents(id)
  }

  @Get("getTemplateElements")
  getTemplateElements(
    @Query("id") id: string,
    @Query("isSubmodule") isSubmodule?: boolean,
  ) {
    return this.service.getTemplateElements(id, isSubmodule)
  }

  @Post("saveTemplateInDashboard")
  saveTemplateInDashboard(
    @Query("id") id: string,
    @Body() template: Template,
  ) {
    return this.service.saveTemplateInDashboard(id, template)
  }

  @Post("saveTemplatesInDashboard")
  saveTemplatesInDashboard(
    @Query("id") id: string,
    @Body() templates: Template[],
  ) {
    return this.service.saveTemplatesInDashboard(id, templates)
  }

  @Delete("deleteTemplatesInDashboard")
  deleteTemplatesInDashboard(
    @Query("ids", new ParseArray({type: String, separator: ","}))
    ids: string[]
  ) {
    return this.service.deleteTemplatesInDashboard(ids)
  }

  @Post("updateTemplatesInDashboard")
  updateTemplatesInDashboard(
    @Query("id") dashboardId: string,
    @Body() templates: Template[],
  ) {
    return this.service.updateTemplatesInDashboard(dashboardId, templates)
  }

  @Post("modifyTemplate")
  modifyTemplate(
    @Query("id") id: string,
    @Body() template: Template,
  ) {
    return this.service.modifyTemplate(id, template)
  }

  @Post("copyTemplateElements")
  copyTemplateElements(
    @Body(TemplateCopyElementsPipe) cpy: TemplateCopyElementsDto
  ) {
    return this.service.copyTemplateElements(cpy.originTemplateId, cpy.targetTemplateId)
  }

  @Post("updateTemplateElements")
  updateTemplateElements(@Body() template: Template) {
    return this.service.updateTemplateElements(template)
  }
}

