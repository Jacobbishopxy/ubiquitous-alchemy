package gallery

import "ubiquitous-biz-server/api/entity"

type Template struct {
	entity.UintID
	Index       uint
	Name        string `gorm:"size:256;not null" `
	Description string
	Elements    []*Element
}
