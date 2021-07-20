package entity

import "time"

type Action string

const (
	CREATE Action = "create"
	READ   Action = "read"
	UPDATE Action = "update"
	DELETE Action = "delete"
)

type UnitId struct {
	Id uint `gorm:"primaryKey;autoIncrement"`
}

type Common struct {
	UnitId
	CreatedAt time.Time `gorm:"autoCreateTime"`
	UpdatedAt time.Time `gorm:"autoUpdateTime"`
}

type PaginationM10 struct {
	Limit  uint
	Offset uint
}
