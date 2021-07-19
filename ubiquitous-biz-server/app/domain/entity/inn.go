package entity

import (
	"html"
	"strings"
	"time"

	"ubiquitous-biz-server/app/util"
)

type Tag struct {
	Common
	Name        string     `gorm:"size:100;not null;unique" json:"name" binding:"required"`
	Description string     `gorm:"size:100" json:"description,omitempty"`
	Color       string     `gorm:"size:100" json:"color,omitempty"`
	Articles    []*Article `gorm:"many2many:article_tag" json:"articles,omitempty"`
}

type Article struct {
	Common
	Title   string `gorm:"size:100;not null" json:"title" binding:"required"`
	Content string `gorm:"text;not null" json:"content" binding:"required"`
	Tags    []*Tag `gorm:"many2many:article_tag" json:"tags,omitempty"`
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
