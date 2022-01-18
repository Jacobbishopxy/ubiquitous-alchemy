/**
 * Created by Jacob Xie on 8/29/2020.
 */

import {Body, Controller, Delete, Get, Post, Query} from '@nestjs/common'

import {ElementService} from "../provider"
import {Element} from "../entity"


@Controller()
export class ElementController {
  constructor(private readonly service: ElementService) {}

  @Get("elements")
  getAllElements() {
    return this.service.getAllElements()
  }

  @Get("element")
  getElementsByIds(@Query("ids") ids: string) {
    return this.service.getElementsByIds(ids.split(","))
  }

  @Post("element")
  saveElement(@Body() element: Element) {
    return this.service.saveElement(element)
  }

  @Delete("element")
  deleteElement(@Query("id") id: string) {
    return this.service.deleteElement(id)
  }

  // ===================================================================================================================

  @Get("getElementContentDates")
  getElementContentDates(
    @Query("id") id: string,
    @Query("markName") markName?: string
  ) {
    return this.service.getElementContentDates(id, markName)
  }

  @Get("getElementContent")
  getElementContent(
    @Query("id") id: string,
    @Query("date") date?: string,
    @Query("markName") markName?: string,
  ) {
    return this.service.getElementContentAndFetchQuery(id, date, markName)
  }

  @Post("modifyElement")
  modifyElement(
    @Query("id") id: string,
    @Body() element: Element,
  ) {
    return this.service.modifyElement(id, element)
  }

  @Post("updateElements")
  updateElements(@Body() elements: Element[]) {
    return this.service.saveElements(elements)
  }
}

