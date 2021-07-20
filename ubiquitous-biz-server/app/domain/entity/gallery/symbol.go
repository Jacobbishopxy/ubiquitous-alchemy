package gallery

import "ubiquitous-biz-server/app/domain/entity"

type Symbol struct {
	entity.Common
	Name        string `gorm:"size:256;not null;unique"`
	Type        string `gorm:"szie:100"`
	Description string
	Categories  []*Category `gorm:"many2many:category_symbol"`
}
