package gallery

import "ubiquitous-biz-server/api/entity"

type PublicTag struct {
	entity.UintID
	Name        string `gorm:"size:256;not null;unique"`
	Type        string `gorm:"szie:100"`
	Description string
	Color       string     `gorm:"size:100"`
	Categories  []Category `gorm:"many2many:category_public_tag"`
	Pointers    []Pointer  `gorm:"many2many:public_tag_pointer"`
}
