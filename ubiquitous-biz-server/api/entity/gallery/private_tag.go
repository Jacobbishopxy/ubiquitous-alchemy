package gallery

import "ubiquitous-biz-server/api/entity"

type PrivateTag struct {
	entity.UintID
	Name        string `gorm:"size:256;not null"`
	Type        string `gorm:"szie:100"`
	Description string
	Color       string `gorm:"size:100"`
	Category    Category
	Pointers    []Pointer `gorm:"many2many:private_tag_pointer"`
}
