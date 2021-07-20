package gallery

import "ubiquitous-biz-server/app/domain/entity"

type Mark struct {
	entity.Common
	Name        string `gorm:"size:256;not null"`
	Description string
	Category    Category
	Contents    []*Content `gorm:"many2many:content_mark"`
}
