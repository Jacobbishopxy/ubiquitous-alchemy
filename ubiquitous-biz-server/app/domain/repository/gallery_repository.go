package repository

import "ubiquitous-biz-server/app/domain/entity/gallery"

type GalleryQuery interface {
	ShowAllCategories() ([]gallery.Category, error)

	ShowAllPublicTags() ([]gallery.PublicTag, error)

	CategoryShowAllPrivateTags(categoryId uint) ([]gallery.PrivateTag, error)

	CategoryShowAllDashboards(categoryId uint) ([]gallery.Dashboard, error)

	CategoryShowAllPointers(categoryId uint) ([]gallery.Pointer, error)

	DashboardShowAllTemplates(dashboardId uint) ([]gallery.Template, error)

	TemplateShowAllElements(templateId uint) ([]gallery.Element, error)

	ElementShowAllPointers(elementId uint) ([]gallery.Pointer, error)

	ElementShowPointer(elementId uint, condition interface{}) (gallery.Pointer, error)

	ShowAllPointers(condition interface{}) ([]gallery.Pointer, error)
}

type GalleryMutation interface {
	// Supervisor
	CreateCategory(category *gallery.Category) (*gallery.Category, error)
	// Supervisor
	DeleteCategory(categoryId uint) error
	// Admin
	CreatePublicTag(publicTag *gallery.PublicTag) (*gallery.PublicTag, error)
	// Admin
	DeletePublicTag(publicTagId uint) error

	// Editor
	AttachPublicTagToCategory(categoryId, publicTagId uint) error
	// Editor
	ModifyPublicTagsInCategory(categoryId uint, publicTagIds []uint) error
	// Editor
	DetachPublicTagFromCategory(categoryId, publicTagId uint) error

	// Editor
	CreatePrivateTagToCategory(categoryId uint, privateTag *gallery.PrivateTag) (*gallery.PrivateTag, error)
	// Editor
	DeletePrivateTagFromCategory(privateTagId, categoryId uint) error

	// Editor
	CategoryCreateDashboard(categoryId uint, dashboard *gallery.Dashboard) (*gallery.Dashboard, error)
	// Editor
	DashboardCreateTemplate(dashboardId uint, template *gallery.Template) (*gallery.Template, error)
	// Editor
	DashboardModifyTemplate(dashboardId uint, template *gallery.Template) (*gallery.Template, error)
	// Editor
	DashboardDeleteTemplate(dashboardId, templateId uint) error
	// Editor
	DashboardCopyTemplate(originalDashboardId, originalTemplateId, targetDashboardId, targetTemplateId uint) error

	// Editor
	TemplateCreateElement(templateId uint, element *gallery.Element) (*gallery.Element, error)
	// Editor
	TemplateModifyElements(templateId uint, elements []gallery.Element) ([]gallery.Element, error)
	// Editor
	TemplateDeleteElement(templateId, elementId uint) error

	// Editor
	CreatePointer(pointer *gallery.Pointer) (*gallery.Pointer, error)
	// Editor
	DeletePointer(pointerId uint) error
	// Editor
	AttachPointerToCategory(categoryId, pointerId uint) error
	// Editor
	DetachPointerFromCategory(categoryId, pointerId uint) error
	// Editor
	AttachPointerToElement(elementId, pointerId uint) error
	// Editor
	DetachPointerFromElement(elementId, pointerId uint) error

	// Editor
	AttachPublicTagToPointer(pointerId, publicTagId uint) error
	// Editor
	ModifyPublicTagsInPointer(pointerId uint, publicTagIds []uint) error
	// Editor
	DetachPublicTagFromPointer(pointerId, symbolId uint) error

	// Editor
	AttachPrivateTagToPointer(pointerId, privateId uint) error
	// Editor
	ModifyPrivateTagsInPointer(pointerId uint, privateIds []uint) error
	// Editor
	DetachPrivateTagFromPointer(pointerId, privateId uint) error
}
