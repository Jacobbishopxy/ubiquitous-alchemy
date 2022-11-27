/**
 * Created by Jacob Xie on 9/8/2020.
 */

import {Body, Controller, Get, HttpException, HttpStatus, Post, Query, Req} from '@nestjs/common'
import {Request} from "express"

import {AuthorService, AuthService} from "../provider"
import {AuthorDashboardDto} from "../dto"
import * as common from "../common"

@Controller()
export class AuthorController {
  constructor(
    private authorService: AuthorService,
    private authService: AuthService,
  ) {}

  @Get("authors")
  async getAllAuthors() {

    const authors = await this.authorService.getAllAuthors(true)
    const users = await this.authService.getAllUserAccounts()

    const usersMap = users.reduce((acc, user) => {
      acc[user.email] = user
      return acc
    }, {})

    const res: common.FullAuthor[] = authors.map(author => {
      var user = usersMap[author.email]
      if (!user) {
        // Temporary disable throw exception, since no uniformed API provided.
        // A default `UserAccount` value has been given to avoid null `user` value

        // throw new HttpException(`User ${author.email} not found`, HttpStatus.NOT_FOUND)
        user = {
          username: "Unknown",
          email: "Unknown",
          active: true,
          roles: [],
          privileges: []
        } as common.UserAccount
      }
      return {...author, ...user}
    })

    return res
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
    let userAcc = await this.authService.getUserAccount(request)
    if (!authorizedPrivileges(userAcc.privileges)) {
      throw new HttpException(`Unauthorized role: ${userAcc.privileges}`, HttpStatus.UNAUTHORIZED)
    }

    return this.authorService.bindDashboardsToAuthor(binding.email, binding.dashboardIds)
  }

  @Post("unbindDashboardsFromAuthor")
  async unbindDashboardsFromAuthor(
    @Req() request: Request,
    @Body() binding: AuthorDashboardDto,
  ) {
    let userAcc = await this.authService.getUserAccount(request)
    if (!authorizedPrivileges(userAcc.privileges)) {
      throw new HttpException(`Unauthorized role: ${userAcc.privileges}`, HttpStatus.UNAUTHORIZED)
    }

    return this.authorService.unbindDashboardsFromAuthor(binding.email, binding.dashboardIds)
  }

  @Post("updateAuthorDashboards")
  async updateAuthorDashboards(
    @Req() request: Request,
    @Body() binding: AuthorDashboardDto,
  ) {
    let userAcc = await this.authService.getUserAccount(request)
    if (!authorizedPrivileges(userAcc.privileges)) {
      throw new HttpException(`Unauthorized role: ${userAcc.privileges}`, HttpStatus.UNAUTHORIZED)
    }

    return this.authorService.updateAuthorDashboards(binding.email, binding.dashboardIds)
  }
}

const authorizedPrivileges = (privilege: common.UserPrivilege[]): boolean => {
  for (let p of privilege) {
    if (p.name === "write") {
      return true
    }
  }

  return false
}
