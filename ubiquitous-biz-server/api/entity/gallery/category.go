package gallery

import (
	"ubiquitous-biz-server/api/entity"
)

type CategoryBase struct {
	entity.UintID
	Name        string `gorm:"size:256;not null;unique"`
	Type        string `gorm:"szie:100;not null"`
	Description string
}

type Category struct {
	CategoryBase
	PublicTags  []PublicTag `gorm:"many2many:category_symbol"`
	PrivateTags []PrivateTag
	Dashboards  []Dashboard
	Pointers    []Pointer
}
