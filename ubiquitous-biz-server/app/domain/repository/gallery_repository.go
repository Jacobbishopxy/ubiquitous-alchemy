package repository

import "ubiquitous-biz-server/app/domain/entity/gallery"

type Gallery interface {
	NewCategory(category *gallery.Category) (*gallery.Category, error)
	NewSymbol(symbol *gallery.Symbol) (*gallery.Symbol, error)

	AttachSymbolToCategory(symbolId, categoryId uint) (bool, error)
	ModifySymbolInCategory(symbolId, categoryId uint) (bool, error)
	RemoveSymbolFromCategory(symbolId, categoryId uint) (bool, error)

	CategoryNewMark(categoryId uint, mark *gallery.Mark) (*gallery.Mark, error)
	CategoryRemoveMark(categoryId, markId uint) (bool, error)
	CategoryNewDashboard(categoryId uint, dashboard *gallery.Dashboard) (*gallery.Dashboard, error)

	DashboardNewTemplate(dashboardId uint, template *gallery.Template) (*gallery.Template, error)
	DashboardModifyTemplate(dashboardId uint, template *gallery.Template) (*gallery.Template, error)
	DashboardRemoveTemplate(dashboardId, templateId uint) (bool, error)

	TemplateNewElement(templateId uint, element *gallery.Element) (*gallery.Element, error)
	TemplateModifyElements(templateId uint, elements []gallery.Element) ([]gallery.Element, error)
	TemplateRemoveElement(templateId, elementId uint) (bool, error)

	NewContent(content *gallery.Content) (*gallery.Content, error)
	AttachSymbolToContent(symbolId, contentId uint) (bool, error)
	ModifySymbolInContent(symbolId, contentId uint) (bool, error)
	RemoveSymbolFromContent(symbolId, contentId uint) (bool, error)
	AttachMarksToContent(markIds []uint, contentId uint) (bool, error)
	ModifyMarksInContent(markIds []uint, contentId uint) (bool, error)
	RemoveMarksFromContent(markIds []uint, contentId uint) (bool, error)
}
