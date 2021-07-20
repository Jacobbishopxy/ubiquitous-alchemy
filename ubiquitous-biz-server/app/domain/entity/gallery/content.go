package gallery

import (
	"time"
	"ubiquitous-biz-server/app/domain/entity"

	"gorm.io/datatypes"
)

type Content struct {
	entity.Common
	Date     time.Time `grom:"default:CURRENT_TIMESTAMP"`
	Title    string    `gorm:"size:256;not null" `
	Data     datatypes.JSONMap
	Config   *datatypes.JSONMap
	Category *Category
	Element  *Element
	Symbol   *Symbol
	Marks    []*Mark `gorm:"many2many:content_mark"`
}
