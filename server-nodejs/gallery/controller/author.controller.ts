/**
 * Created by Jacob Xie on 9/8/2020.
 */

import {Body, Controller, Get, HttpException, HttpStatus, Post, Query, Req} from '@nestjs/common'
import {Request} from "express"

import {AuthorService, AuthService} from "../provider"
import {AuthorDashboardDto} from "../dto"

@Controller()
export class AuthorController {
  constructor(
    private authorService: AuthorService,
    private authService: AuthService,
  ) {}

  @Get("authors")
  getAllAuthors() {
    return this.authorService.getAllAuthors()
  }

  @Get("author")
  getAuthorByEmail(@Query("email") email: string) {
    return this.authorService.getAuthorByEmail(email)
  }

  @Post("bindDashboardsToAuthor")
  async bindDashboardsToAuthor(
    @Req() request: Request,
    @Body() binding: AuthorDashboardDto,
  ) {
    let auth = await this.authService.getUserInfo(request)
    if (!authorizedRoles(auth.role)) {
      throw new HttpException(`Unauthorized role: ${auth.role}`, HttpStatus.UNAUTHORIZED)
    }
    return this.authorService.bindDashboardsToAuthor(binding.email, binding.dashboardIds)
  }

  @Post("unbindDashboardsFromAuthor")
  async unbindDashboardsFromAuthor(
    @Req() request: Request,
    @Body() binding: AuthorDashboardDto,
  ) {
    let auth = await this.authService.getUserInfo(request)
    if (!authorizedRoles(auth.role)) {
      throw new HttpException(`Unauthorized role: ${auth.role}`, HttpStatus.UNAUTHORIZED)
    }
    return this.authorService.unbindDashboardsFromAuthor(binding.email, binding.dashboardIds)
  }

  @Post("updateAuthorDashboards")
  async updateAuthorDashboards(
    @Req() request: Request,
    @Body() binding: AuthorDashboardDto,
  ) {
    let auth = await this.authService.getUserInfo(request)
    if (!authorizedRoles(auth.role)) {
      throw new HttpException(`Unauthorized role: ${auth.role}`, HttpStatus.UNAUTHORIZED)
    }
    return this.authorService.updateAuthorDashboards(binding.email, binding.dashboardIds)
  }
}

const authorizedRoles = (role: string): boolean => {
  return role === "admin" || role === "supervisor"
}
