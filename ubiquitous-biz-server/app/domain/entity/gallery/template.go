package gallery

import "ubiquitous-biz-server/app/domain/entity"

type Template struct {
	entity.UnitId
	Index       uint
	Name        string `gorm:"size:256;not null" `
	Description string
	Elements    []*Element
}
