/**
 * Created by Jacob Xie on 1/14/2021.
 */

import {HttpException, HttpStatus, Injectable} from "@nestjs/common"
import {InjectRepository} from "@nestjs/typeorm"
import {Repository} from "typeorm"

import * as common from "../common"
import {Author} from "../entity"


@Injectable()
export class AuthorService {
  constructor(
    @InjectRepository(Author, common.db)
    private readonly authorRepository: Repository<Author>,
  ) {}

  getAllAuthorsAndDashboard() {

    // TODO: get all authors and their dashboard

  }
}
