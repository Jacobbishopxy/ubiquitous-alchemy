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

  private getGatewayPath() {
    const serverConfig = this.configService.get("server")

    return `http://${serverConfig.SERVER_GATEWAY_HOST}:${serverConfig.SERVER_GATEWAY_PORT}/gateway/api/user`
  }

  async getUserInfo(req: Request): Promise<common.Auth> {
    const token = req.cookies[common.auth]


    if (!token)
      throw new HttpException("Unauthorized", HttpStatus.UNAUTHORIZED)

    const response = await axios.get(`${this.getGatewayPath()}`, {
      headers: {
        Cookie: `${common.auth}=${token}`,
      },
    })

    if (response.status === HttpStatus.OK) {
      return response.data
    } else {
      throw new HttpException("Unauthorized", HttpStatus.UNAUTHORIZED)
    }
  }
}

