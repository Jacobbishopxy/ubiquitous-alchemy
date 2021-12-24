package gallery

import "ubiquitous-biz-server/api/entity"

type Dashboard struct {
	entity.UintID
	Name        string `gorm:"size:256;not null"`
	Description string
	Templates   []*Template
	Category    Category
}
