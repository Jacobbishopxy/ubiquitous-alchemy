/**
 * Created by Jacob Xie on 1/17/2021.
 */

import {IsNotEmpty, IsArray, IsString} from "class-validator"

export class AuthorDashboardDto {
  @IsString()
  @IsNotEmpty()
  email!: string

  @IsArray()
  dashboardIds!: string[]
}

