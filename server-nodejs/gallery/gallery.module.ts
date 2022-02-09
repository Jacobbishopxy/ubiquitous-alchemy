/**
 * Created by Jacob Xie on 10/26/2020.
 */

import {Module} from "@nestjs/common"
import {TypeOrmModule} from "@nestjs/typeorm"
import {db, dbExt} from "./common"

import {
  Author,
  Category,
  Content,
  Dashboard,
  Element,
  Mark,
  Storage,
  Tag,
  Template,
  Record,
  FlexContent,
} from "./entity"
import {
  AuthService,
  AuthorService,
  CategoryService,
  ContentService,
  DashboardService,
  ElementService,
  MarkService,
  StorageService,
  TagService,
  TemplateService,
  MongoService,
  RecordService,
  FlexContentService,
} from "./provider"
import {StorageSubscriber} from "./subscriber"
import {
  AuthorController,
  CategoryController,
  ContentController,
  DashboardController,
  ElementController,
  MarkController,
  StorageController,
  TagController,
  TemplateController,
  ContentMongoController,
  RecordController,
  FlexContentController,
} from "./controller"


@Module({
  imports: [
    TypeOrmModule.forFeature([
      Author,
      Category,
      Content,
      Dashboard,
      Element,
      Mark,
      Storage,
      Tag,
      Template,
      Record,
    ], db),
    TypeOrmModule.forFeature([
      FlexContent,
    ], dbExt)
  ],
  providers: [
    AuthService,
    AuthorService,
    CategoryService,
    ContentService,
    DashboardService,
    ElementService,
    MarkService,
    StorageService,
    TagService,
    TemplateService,
    StorageSubscriber,
    MongoService,
    RecordService,
    FlexContentService,
  ],
  controllers: [
    AuthorController,
    CategoryController,
    ContentController,
    DashboardController,
    ElementController,
    MarkController,
    StorageController,
    TagController,
    TemplateController,
    ContentMongoController,
    RecordController,
    FlexContentController,
  ],

})
export class GalleryModule {}

