package gallery

import "ubiquitous-biz-server/app/domain/entity"

type Dashboard struct {
	entity.Common
	Name        string `gorm:"size:256;not null"`
	Description string
	Templates   []*Template
	Category    Category
}
