/**
 * Created by Jacob Xie on 9/16/2020.
 */

import {Injectable} from "@nestjs/common"
import {InjectRepository} from "@nestjs/typeorm"
import {Repository} from "typeorm"
import {Request} from "express"
import _ from "lodash"

import * as common from "../common"
import * as utils from "../../utils"
import {Dashboard, Category} from "../entity"
import {AuthService, AuthorService} from "."


const dashboardFullRelations = {
  relations: [
    common.category,
    common.templates,
    common.templatesElements,
    common.templatesElementsContents
  ]
}

const dashboardTemplateRelations = {
  relations: [
    common.category,
    common.templates
  ]
}

const categoryDashboardRelations = {
  relations: [common.dashboards]
}

@Injectable()
export class DashboardService {
  constructor(
    @InjectRepository(Dashboard, common.db)
    private repoDashboard: Repository<Dashboard>,
    @InjectRepository(Category, common.db)
    private repoCategory: Repository<Category>,
    private authorService: AuthorService,
    private authService: AuthService,
  ) {}

  getAllDashboards() {
    return this.repoDashboard.find(dashboardFullRelations)
  }

  getDashboardById(id: string) {
    return this.repoDashboard.findOne({
      ...dashboardFullRelations,
      ...utils.whereIdEqual(id)
    })
  }

  saveDashboard(dashboard: Dashboard) {
    const newDsb = this.repoDashboard.create(dashboard)
    return this.repoDashboard.save(newDsb)
  }

  deleteDashboard(id: string) {
    return this.repoDashboard.delete(id)
  }

  // ===================================================================================================================

  getAllDashboardsTemplate() {
    return this.repoDashboard.find(dashboardTemplateRelations)
  }

  getDashboardCategoryAndTemplate(dashboardId: string) {
    return this.repoDashboard.findOne({
      ...dashboardTemplateRelations,
      ...utils.whereIdEqual(dashboardId)
    })
  }

  async modifyDashboard(dashboard: Dashboard) {
    if (dashboard.id) {
      const dsb = await this.repoDashboard.findOne({
        ...utils.whereIdEqual(dashboard.id)
      })

      if (dsb) {
        const newDsb = this.repoDashboard.create({
          ...dsb,
          name: dashboard.name,
          description: dashboard.description
        })
        await this.repoDashboard.save(newDsb)
        return true
      }
    }

    return false
  }

  async newDashboardAttachToCategory(categoryName: string, dashboard: Dashboard) {
    const cat = await this.repoCategory.findOne({
      ...categoryDashboardRelations,
      ...utils.whereNameEqual(categoryName)
    })

    if (cat) {
      const newDb = this.repoDashboard.create({
        ...dashboard,
        category: {
          name: categoryName
        },
      })
      await this.repoDashboard.save(newDb)

      return true
    }

    return false
  }

  deleteDashboardInCategory(categoryName: string, dashboardName: string) {
    return this.repoDashboard
      .createQueryBuilder(common.dashboard)
      .leftJoinAndSelect(common.dashboardCategory, common.dashboard)
      .select([common.dashboardName, common.categoryName])
      .where(`${common.categoryName} = :categoryName AND ${common.dashboardName} = :dashboardName`, {
        categoryName,
        dashboardName
      })
      .delete()
      .execute()
  }

  saveDashboards(dashboards: Dashboard[]) {
    const newDashboards = dashboards.map(d => this.repoDashboard.create(d))
    return this.repoDashboard.save(newDashboards)
  }

  deleteDashboards(dashboardIds: string[]) {
    return this.repoDashboard.delete(dashboardIds)
  }

  /*
  This method is a CRUD API for dashboard in a category.
  Automatically bind the author from the request into the dashboard.
  */
  async updateDashboardsInCategory(
    request: Request,
    categoryName: string,
    dashboards: Dashboard[]
  ) {
    const cat = await this.repoCategory.findOne({
      ...categoryDashboardRelations,
      ...utils.whereNameEqual(categoryName)
    })

    if (cat) {
      // check difference between old and new dashboards
      const dashboardsRemove = _.differenceWith(
        cat.dashboards, dashboards, (prev, curr) => prev.id === curr.id
      )

      // delete dashboards as well as their authors' bindings
      if (dashboardsRemove.length > 0)
        await this.deleteDashboards(dashboardsRemove.map(d => d.id))

      // save dashboards. notice that brand new dashboards (without id) will be created
      // and unchanged dashboards (with id) will be updated
      let upsertDashboards = dashboards.map(d => ({...d, category: {name: categoryName}})) as Dashboard[]
      upsertDashboards = await this.saveDashboards(upsertDashboards)

      // only if author is `editor` we need to auto bind the new dashboard
      // let author = await this.authService.getUserInfo(request)
      let author = await this.authService.getUserAccount(request)

      if (authorizedRoles(author.roles)) {
        // only new ids should be bind to the author who created the dashboards,
        // get unchanged ids in order to compare with the new ids
        let unchangedDashboardIds = dashboards.filter(d => d.id !== undefined).map(d => d.id)
        let upsertDashboardIds = upsertDashboards.map(d => d.id)

        // bind new dashboards to author, we don't need to concern about unbind
        // because `deleteDashboards()` will automatically unbind author relation
        let newDashboardsIds = _.difference(upsertDashboardIds, unchangedDashboardIds)
        await this.authorService.bindDashboardsToAuthor(author.email, newDashboardsIds)
      }
    }

    return this.repoCategory.findOne({
      ...categoryDashboardRelations,
      ...utils.whereNameEqual(categoryName)
    })
  }

  async searchDashboards(keyword: string) {
    return this.repoDashboard.find({
      ...dashboardTemplateRelations,
      ...utils.whereNameLike(keyword)
    })
  }

  getAllDashboardsSimple() {
    return this.repoDashboard.find()
  }
}

const authorizedRoles = (roles: common.UserRole[]) => {
  for (let r of roles) {
    if (r.name === "editor") {
      return true
    }
  }
  return false
}
