/**
 * Created by Jacob Xie on 10/21/2020.
 */

import path from "path"
import dotenv from 'dotenv'
import {Routes, RouterModule} from "nest-router"
import {TypeOrmModule} from "@nestjs/typeorm"
import {ConfigModule, registerAs} from "@nestjs/config"

import {
  Author,
  Category,
  Content,
  Dashboard,
  Element,
  Mark,
  Storage,
  Tag,
  Template
} from "./gallery/entity"
import {CollectionModule} from "./collection/collection.module"
import {GalleryModule} from "./gallery/gallery.module"
import {
  Update,
  Tag as UpdateTag
} from "./inn/entity"
import {InnModule} from "./inn/inn.module"

// since production mode's main file is under /server-nodejs/backend/ directory,
// rootDir is relatively 1 layer higher than dev mode.
const isProd = process.env.NODE_ENV === "production"
const rootDir = isProd ? path.join(__dirname, "../..") : path.join(__dirname, "..")


interface ENV {
  SERVER_PY_HOST: string
  SERVER_PY_PORT: number
  SERVER_GO_HOST: string
  SERVER_GO_PORT: number

  GALLERY_NAME: string
  GALLERY_TYPE: string
  GALLERY_HOST: string
  GALLERY_PORT: number
  GALLERY_USERNAME: string
  GALLERY_PASSWORD: string
  GALLERY_DATABASE: string
  GALLERY_SYNCHRONIZE: boolean
  GALLERY_AUTO_LOAD_ENTITIES: boolean
  GALLERY_LOGGING: boolean
  GALLERY_UUID_EXTENSION: string

  INN_NAME: string
  INN_TYPE: string
  INN_SYNCHRONIZE: boolean
  INN_AUTO_LOAD_ENTITIES: boolean
  INN_LOGGING: boolean

  FM_ROOT: string
}


/**
 * read config file. If `config.json` not existed, use `config.template.json`
 */
const readConfig = (): ENV => {
  dotenv.config({path: path.join(rootDir, "./resources/secret.env")})

  return {
    SERVER_PY_HOST: process.env.SERVER_PY_HOST || "localhost",
    SERVER_PY_PORT: Number(process.env.SERVER_PY_PORT) || 8020,
    SERVER_GO_HOST: process.env.SERVER_GO_HOST || "localhost",
    SERVER_GO_PORT: Number(process.env.SERVER_GO_PORT) || 8040,

    GALLERY_NAME: process.env.GALLERY_NAME || "gallery",
    GALLERY_TYPE: process.env.GALLERY_TYPE || "postgres",
    GALLERY_HOST: process.env.GALLERY_HOST || "localhost",
    GALLERY_PORT: Number(process.env.GALLERY_PORT) || 5432,
    GALLERY_USERNAME: process.env.GALLERY_USERNAME || "root",
    GALLERY_PASSWORD: process.env.GALLERY_PASSWORD || "secret",
    GALLERY_DATABASE: process.env.GALLERY_DATABASE || "dev",
    GALLERY_SYNCHRONIZE: Boolean(process.env.GALLERY_SYNCHRONIZE) || true,
    GALLERY_AUTO_LOAD_ENTITIES: Boolean(process.env.GALLERY_AUTO_LOAD_ENTITIES) || true,
    GALLERY_LOGGING: Boolean(process.env.GALLERY_LOGGING) || false,
    GALLERY_UUID_EXTENSION: process.env.GALLERY_UUID_EXTENSION || "uuid-ossp",

    INN_NAME: process.env.INN_NAME || "inn",
    INN_TYPE: process.env.INN_TYPE || "sqlite",
    INN_SYNCHRONIZE: Boolean(process.env.INN_SYNCHRONIZE) || true,
    INN_AUTO_LOAD_ENTITIES: Boolean(process.env.INN_AUTO_LOAD_ENTITIES) || true,
    INN_LOGGING: Boolean(process.env.INN_LOGGING) || false,

    FM_ROOT: process.env.FM_ROOT || "/home",
  }
}

const config = readConfig()

/**
 * Global configuration
 */
const configLoad = registerAs("server", () => config)
const generalConfig = registerAs("general", () => ({
  path: path.join(rootDir, "./public")
}))
const configImport = ConfigModule.forRoot({
  load: [configLoad, generalConfig],
  isGlobal: true,
  ignoreEnvFile: true,
})


/**
 * API routes
 */
const routes: Routes = [
  {
    path: "/api",
    module: CollectionModule,
    children: [
      {
        path: "/gallery",
        module: GalleryModule
      },
      {
        path: "/inn",
        module: InnModule
      }
    ]
  }
]
const routerImports = RouterModule.forRoutes(routes)

/**
 * database gallery module
 */
const galleryEntities = [
  Author,
  Category,
  Content,
  Dashboard,
  Element,
  Mark,
  Storage,
  Tag,
  Template,
]

const databaseGalleryImports = TypeOrmModule.forRoot({
  name: config.GALLERY_NAME,
  type: config.GALLERY_TYPE as any,
  host: config.GALLERY_HOST,
  port: config.GALLERY_PORT,
  username: config.GALLERY_USERNAME,
  password: config.GALLERY_PASSWORD,
  database: config.GALLERY_DATABASE,
  synchronize: config.GALLERY_SYNCHRONIZE,
  autoLoadEntities: config.GALLERY_AUTO_LOAD_ENTITIES,
  logging: config.GALLERY_LOGGING,
  uuidExtension: config.GALLERY_UUID_EXTENSION as any,
  entities: galleryEntities,
})

/**
 * database inn module
 */
const innEntities = [
  Update,
  UpdateTag
]

const databaseInnImports = TypeOrmModule.forRoot({
  name: config.INN_NAME,
  type: config.INN_TYPE as any,
  synchronize: config.INN_SYNCHRONIZE,
  autoLoadEntities: config.INN_AUTO_LOAD_ENTITIES,
  logging: config.INN_LOGGING,
  entities: innEntities,
  database: `${rootDir}/inn/inn.sqlite`
})

export {
  configImport,
  routerImports,
  databaseGalleryImports,
  databaseInnImports
}
