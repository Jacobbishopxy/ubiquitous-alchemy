/**
 * Created by Jacob Xie on 10/26/2020.
 */

import {Module} from "@nestjs/common"
import {TypeOrmModule} from "@nestjs/typeorm"
import {db} from "./common"

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
    ], db)
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
  ],

})
export class GalleryModule {}

