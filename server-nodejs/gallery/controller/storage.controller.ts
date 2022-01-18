/**
 * Created by Jacob Xie on 10/22/2020.
 */

import {Body, Controller, Delete, Get, Post, Query} from '@nestjs/common'

import {StorageService} from "../provider"
import {Storage} from "../entity"
import {ReadDto} from "../dto"
import {ReadPipe} from "../pipe"


@Controller()
export class StorageController {
  constructor(private service: StorageService) {}

  @Get("storages")
  getAllStorages() {
    return this.service.getAllStorages()
  }

  @Get("storage")
  getStorageById(@Query("id") id: string) {
    return this.service.getStorageById(id)
  }

  @Post("storage")
  saveStorage(@Body() storage: Storage) {
    return this.service.saveStorage(storage)
  }

  @Delete("storage")
  deleteStorage(@Query("id") id: string) {
    return this.service.deleteStorage(id)
  }

  // ===================================================================================================================

  @Get("getAllStorageSimple")
  getAllStorageSimple() {
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
  executeSql(
    @Query("id") id: string,
    @Query("sqlString") sqlString: string,
  ) {
    return this.service.executeSql(id, sqlString)
  }

  @Post("read")
  read(
    @Query("id") id: string,
    @Query("databaseType") databaseType: string,
    @Body(ReadPipe) readDto: ReadDto,
  ) {
    return this.service.readFromDB(id, readDto, databaseType)
  }
}

