/**
 * Created by Jacob Xie on 8/29/2020.
 */

import {Body, Controller, Delete, Get, ParseArrayPipe, Post, Query} from '@nestjs/common'

import {ContentService} from "../provider"
import * as common from "../common"
import {Content} from "../entity"
// import * as MongoService from "../provider/contentMongo.service"

@Controller()
export class ContentController {
    constructor(private service: ContentService) {}

    @Get("contents")
    getAllContents() {
        return this.service.getAllContents()
    }

    @Get("content")
    getContentById(@Query("id") id: string) {
        return this.service.getContentById(id)
    }

    @Post("content")
    saveContent(@Body() content: Content) {
        return this.service.saveContent(content)
    }

    @Delete("content")
    deleteContent(@Query("id") id: string) {
        return this.service.deleteContent(id)
    }

    @Delete("deleteContents")
    deleteContents(@Query("ids") ids: string) {
        let idList = ids.split(",")
        return this.service.deleteContents(idList)
    }

    // ===================================================================================================================

    @Get("getContentsInCategoryByElementTypeAndMarkAndTags")
    getContentsInCategoryByElementTypeAndMarkAndTags(
        @Query("categoryName") categoryName: string,
        @Query("elementType") elementType?: common.ElementType,
        @Query("markName") markName?: string,
        @Query("tagNames") tagNames?: string[],
        @Query("pagination", new ParseArrayPipe({
            optional: true,
            items: Number,
            separator: ","
        })) pagination?: [number, number]) {
        return this.service
            .getContentsInCategoryByElementTypeAndMarkAndTags(
                categoryName,
                elementType,
                markName,
                tagNames,
                pagination
            )
    }

    @Get("getNestedElementContent")
    getNestedElementContent(@Query("contentId") contentId: string) {
        return this.service.getNestedElementContent(contentId)
    }

    @Post("saveContentInCategory")
    saveContentInCategory(
        @Query("name") name: string,
        @Query("type") type: string,
        @Body() content: Content
    ) {
        return this.service.saveContentToMongoOrPg(name, type, content)
    }
}

