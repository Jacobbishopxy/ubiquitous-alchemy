/**
 * Created by Jacob Xie on 10/22/2020.
 */

import {Body, Controller, Delete, Get, HttpException, HttpStatus, Post, Query, Req} from '@nestjs/common'
import {Request} from 'express'
import crypto from 'crypto'

import * as storageService from "../provider/storage.service"
import {Storage} from "../entity"
import {ReadDto} from "../dto"
import {ReadPipe} from "../pipe"


@Controller()
export class StorageController {
  constructor(private readonly service: storageService.StorageService) {}

  @Get("storages")
  getAllStorages() {
    try {
      return this.service.getAllStorages()
    } catch (err: any) {
      throw new HttpException(err, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  @Get("storage")
  getStorageById(@Query("id") id: string) {
    try {
      return this.service.getStorageById(id)
    } catch (err: any) {
      throw new HttpException(err, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  @Post("storage")
  saveStorage(@Body() storage: Storage) {
    try {
      return this.service.saveStorage(storage)
    } catch (err: any) {
      throw new HttpException(err, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  @Delete("storage")
  deleteStorage(@Query("id") id: string) {
    try {
      return this.service.deleteStorage(id)
    } catch (err: any) {
      throw new HttpException(err, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  // ===================================================================================================================

  // TODO: test case
  @Get("getAllStorageSimple")
  getAllStorageSimple(@Req() req: Request) {

    console.log(req.cookies)

    let key = "secret_key_for_hash_password_and_verify"

    let keyBuffer = Buffer.from(key, 'utf8')

    let decipher = crypto.createDecipheriv('aes-256-gcm', keyBuffer, null)

    let decrypted = decipher.update(req.cookies.auth, 'base64', 'utf8')

    decrypted += decipher.final('utf8')

    console.log(decrypted)

    return this.service.getAllStorageSimple()
  }

  @Get("testConnection")
  testConnection(@Query("id") id: string) {
    return this.service.testConnection(id)
  }

  @Get("reloadConnection")
  reloadConnection(@Query("id") id: string) {
    return this.service.reloadConnection(id)
  }

  @Get("executeSql")
  executeSql(@Query("id") id: string,
    @Query("sqlString") sqlString: string) {
    try {
      return this.service.executeSql(id, sqlString)
    } catch (err: any) {
      throw new HttpException(err, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  @Post("read")
  read(@Query("id") id: string, @Query("databaseType") databaseType: string,
    @Body(ReadPipe) readDto: ReadDto) {
    try {
      return this.service.readFromDB(id, readDto, databaseType)
    } catch (err: any) {
      throw new HttpException(err, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }
}

