package entity

import (
	"html"
	"strings"
	"time"
	"ubiquitous-biz-server/app/util"
)

type Tag struct {
	ID          uint64     `gorm:"primary_key;auto_increment" json:"id"`
	Name        string     `gorm:"size:100;not null;unique" json:"name" binding:"required"`
	Description string     `gorm:"size:100" json:"description,omitempty"`
	Color       string     `gorm:"size:100" json:"color,omitempty"`
	Articles    []*Article `gorm:"many2many:article_tags" json:"articles,omitempty"`
}

type Article struct {
	ID        uint64    `gorm:"primary_key;auto_increment" json:"id"`
	Title     string    `gorm:"size:100;not null" json:"title" binding:"required"`
	Content   string    `gorm:"text;not null" json:"content" binding:"required"`
	CreatedAt time.Time `gorm:"default:CURRENT_TIMESTAMP" json:"created_at"`
	UpdatedAt time.Time `gorm:"default:CURRENT_TIMESTAMP" json:"updated_at"`
	Tags      []*Tag    `gorm:"many2many:article_tags" json:"tags,omitempty"`
}

type Pagination struct {
	Limit  uint64 `json:"limit" binding:"required,gte=0,max=10"`
	Offset uint64 `json:"offset" binding:"required,gte=0"`
}

func (t *Tag) BeforeSave() {
	t.Name = html.EscapeString(strings.ToLower(strings.TrimSpace(t.Name)))
	t.Description = html.EscapeString(strings.TrimSpace(t.Description))
	t.Color = html.EscapeString(strings.TrimSpace(t.Color))
}

func (a *Article) BeforeSave() {
	a.Title = html.EscapeString(strings.TrimSpace(a.Title))
	a.CreatedAt = time.Now()
	a.UpdatedAt = time.Now()
}

func (t *Tag) Validate() error {
	var errorMessages error

	if t.Name == "" {
		errorMessages = util.NewBizError("title is required")
	}

	return errorMessages
}

func (a *Article) Validate(action string) error {
	var errorMessages error

	switch strings.ToLower(action) {
	case "update": // occupied in case of modify
		if a.Title == "" {
			errorMessages = util.NewBizError("title is required")
		}
		if a.Content == "" {
			errorMessages = util.NewBizError("content is required")
		}
	default:
		if a.Title == "" {
			errorMessages = util.NewBizError("title is required")
		}
		if a.Content == "" {
			errorMessages = util.NewBizError("content is required")
		}
	}

	return errorMessages
}
