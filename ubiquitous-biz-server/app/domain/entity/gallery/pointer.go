package gallery

import (
	"time"
	"ubiquitous-biz-server/app/domain/entity"

	"gorm.io/datatypes"
)

type Pointer struct {
	entity.Common
	Date        time.Time `grom:"default:CURRENT_TIMESTAMP"`
	Title       string    `gorm:"size:256;not null" `
	Data        datatypes.JSONMap
	Config      *datatypes.JSONMap
	Category    *Category
	Element     *Element
	PublicTags  []PublicTag  `gorm:"many2many:public_tag_pointer"`
	PrivateTags []PrivateTag `gorm:"many2many:private_tag_pointer"`
}
