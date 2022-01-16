/**
 * Created by Jacob Xie on 10/26/2020.
 */

import {NestFactory} from "@nestjs/core"
import {NestExpressApplication} from "@nestjs/platform-express"
import {BadRequestException, ValidationError, ValidationPipe} from "@nestjs/common"
import {json} from "body-parser"
import cookieParser from "cookie-parser"

import {AppModule} from "./app.module"


const port = 8030

async function bootstrap(): Promise<string> {
  // app
  const app = await NestFactory.create<NestExpressApplication>(AppModule)

  // pipe for validation
  app.useGlobalPipes(
    new ValidationPipe({
      exceptionFactory: (validationErrors: ValidationError[] = []) => {
        return new BadRequestException(validationErrors)
      },
    })
  )

  // promote json body limit to 50MB
  app.use(json({limit: "50mb"}))

  // enable cookie parser
  app.use(cookieParser())

  // start server
  await app.listen(port)

  return `App listening on port ${port}`
}

bootstrap()
  .then(res => console.log(res))
  .catch(err => console.log(err))
