/**
 * Created by Jacob Xie on 1/14/2021.
 */

import {HttpException, HttpStatus, Injectable} from "@nestjs/common"
import {InjectRepository} from "@nestjs/typeorm"
import {Repository} from "typeorm"

import * as common from "../common"
import * as utils from "../../utils"
import {Author, Dashboard} from "../entity"


const AuthorFullRelations = {
  relations: [
    common.dashboards
  ]
}

@Injectable()
export class AuthorService {
  constructor(
    @InjectRepository(Author, common.db)
    private repo: Repository<Author>,
  ) {}

  getAllAuthors(active: boolean) {
    if (active) {
      return this.repo.find({
        ...AuthorFullRelations,
        where: {active: true}
      })
    }
    return this.repo.find(AuthorFullRelations)
  }

  getAuthorByEmail(email: string) {
    return this.repo.findOne({
      ...AuthorFullRelations,
      ...utils.whereEmailEqual(email)
    })
  }

  async bindDashboardsToAuthor(
    email: string,
    dashboardIds: string[]
  ) {
    const author = await this.repo.findOne({
      ...AuthorFullRelations,
      ...utils.whereEmailEqual(email)
    })

    if (!author) {
      throw new HttpException(`Author ${email} not found`, HttpStatus.NOT_FOUND)
    }

    const dashboards = dashboardIds.map(id => {
      let d = new Dashboard()
      d.id = id
      return d
    })
    author.dashboards = author.dashboards.concat(dashboards)

    return this.repo.save(author)
  }

  async unbindDashboardsFromAuthor(email: string, dashboardIds: string[]) {
    const author = await this.repo.findOne({
      ...AuthorFullRelations,
      ...utils.whereEmailEqual(email)
    })

    if (!author) {
      throw new HttpException(`Author ${email} not found`, HttpStatus.NOT_FOUND)
    }

    author.dashboards = author.dashboards.filter(dashboard => !dashboardIds.includes(dashboard.id))

    return this.repo.save(author)
  }

  /*
  dashboardIds must always be the full list of dashboard ids
  */
  async updateAuthorDashboards(email: string, dashboardIds: string[]) {
    const author = await this.repo.findOne({
      ...AuthorFullRelations,
      ...utils.whereEmailEqual(email)
    })

    if (!author) {
      throw new HttpException(`Author ${email} not found`, HttpStatus.NOT_FOUND)
    }

    author.dashboards = dashboardIds.map(id => {
      let d = new Dashboard()
      d.id = id
      return d
    })

    return this.repo.save(author)
  }

}
