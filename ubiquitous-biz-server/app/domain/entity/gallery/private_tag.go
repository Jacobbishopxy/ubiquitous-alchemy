package gallery

import "ubiquitous-biz-server/app/domain/entity"

type PrivateTag struct {
	entity.Common
	Name        string `gorm:"size:256;not null"`
	Type        string `gorm:"szie:100"`
	Description string
	Color       string `gorm:"size:100"`
	Category    Category
	Pointers    []Pointer `gorm:"many2many:private_tag_pointer"`
}
