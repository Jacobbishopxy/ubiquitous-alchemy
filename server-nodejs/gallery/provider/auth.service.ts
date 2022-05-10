/**
 * Created by Jacob Xie on 1/14/2021.
 */

import {HttpException, HttpStatus, Injectable} from "@nestjs/common"
import {ConfigService} from "@nestjs/config"
import {Request} from "express"
import axios from 'axios'

import * as common from "../common"

/**
 * Auth Service takes cookie and token from request and check if the user is authenticated
 */
@Injectable()
export class AuthService {
  constructor(private configService: ConfigService) {}

  private getAuthPath() {
    const serverConfig = this.configService.get("server")

    return `http://${serverConfig.SERVER_AUTH_HOST}:${serverConfig.SERVER_AUTH_PORT}`
  }

  async getUserAccount(req: Request): Promise<common.UserAccount> {
    const token = req.cookies[common.userAccountCookie]

    if (!token)
      throw new HttpException("Unauthorized", HttpStatus.UNAUTHORIZED)

    const response = await axios.get(`${this.getAuthPath()}/user`, {
      headers: {
        Cookie: `${common.userAccountCookie}=${token}`,
      },
    })

    if (response.status === HttpStatus.OK) {
      return response.data
    }
    throw new HttpException("Unauthorized", HttpStatus.UNAUTHORIZED)
  }

  async getAllUserAccounts(): Promise<common.UserAccount[]> {
    const response = await axios.get(`${this.getAuthPath()}/users`)

    return response.data
  }
}

