package entity

import "time"

type Id struct {
	ID uint `gorm:"primaryKey;autoIncrement" json:"id"`
}

type Common struct {
	Id
	CreatedAt time.Time `gorm:"autoCreateTime" json:"created_at"`
	UpdatedAt time.Time `gorm:"autoUpdateTime" json:"updated_at"`
}

type PaginationM10 struct {
	Limit  uint `json:"limit" binding:"required,gte=0,max=10"`
	Offset uint `json:"offset" binding:"required,gte=0"`
}
