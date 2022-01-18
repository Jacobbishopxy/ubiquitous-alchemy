/**
 * Created by Jacob Xie on 8/29/2020.
 */

import {Body, Controller, Delete, Get, Post, Query, Req} from '@nestjs/common'
import {Request} from "express"

import {DashboardService} from "../provider"
import {Dashboard} from "../entity"
import {DashboardModifyDto} from "../dto"
import {DashboardModifyPipe, ParseArray} from "../pipe"


@Controller()
export class DashboardController {
  constructor(private readonly service: DashboardService) {}

  @Get("dashboards")
  getAllDashboards() {
    return this.service.getAllDashboards()
  }

  @Get("dashboard")
  getDashboardByName(@Query("name") name: string) {
    return this.service.getDashboardById(name)
  }

  @Post("dashboard")
  saveDashboard(@Body() dashboard: Dashboard) {
    return this.service.saveDashboard(dashboard)
  }

  @Delete("dashboard")
  deleteDashboard(@Query("name") name: string) {
    return this.service.deleteDashboard(name)
  }

  // ===================================================================================================================

  @Get("getAllDashboardsTemplate")
  getAllDashboardsTemplate() {
    return this.service.getAllDashboardsTemplate()
  }

  @Get("getDashboardCategoryAndTemplate")
  getDashboardCategoryAndTemplate(@Query("id") id: string) {
    return this.service.getDashboardCategoryAndTemplate(id)
  }

  @Post("modifyDashboard")
  modifyDashboard(
    @Body(DashboardModifyPipe) dashboard: DashboardModifyDto
  ) {
    return this.service.modifyDashboard(dashboard as Dashboard)
  }

  @Post("newDashboardAttachToCategory")
  newDashboardAttachToCategory(
    @Query("categoryName") categoryName: string,
    @Body() dashboard: Dashboard,
  ) {
    return this.service.newDashboardAttachToCategory(categoryName, dashboard)
  }

  @Delete("deleteDashboardInCategory")
  deleteDashboardInCategory(
    @Query("categoryName") categoryName: string,
    @Query("dashboardName") dashboardName: string
  ) {
    return this.service.deleteDashboardInCategory(categoryName, dashboardName)
  }

  @Post("saveDashboards")
  saveDashboards(@Body() dashboards: Dashboard[]) {
    return this.service.saveDashboards(dashboards)
  }

  @Delete("deleteDashboards")
  deleteDashboards(
    @Query("ids", new ParseArray({type: String, separator: ","}))
    ids: string[]
  ) {
    return this.service.deleteDashboards(ids)
  }

  @Post("updateDashboardsInCategory")
  updateDashboardsInCategory(
    @Req() request: Request,
    @Query("categoryName") categoryName: string,
    @Body() dashboards: Dashboard[]
  ) {
    return this.service.updateDashboardsInCategory(request, categoryName, dashboards)
  }

  @Get("searchDashboards")
  searchDashboards(@Query("keyword") keyword: string) {
    return this.service.searchDashboards(keyword)
  }
}

