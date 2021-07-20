package entity

type Tag struct {
	Common
	Name        string     `gorm:"size:100;not null;unique"`
	Description string     `gorm:"size:100"`
	Color       string     `gorm:"size:100"`
	Articles    []*Article `gorm:"many2many:article_tag;constraint:OnDelete:CASCADE;"`
}

type Article struct {
	Common
	Title   string `gorm:"size:100;not null"`
	Content string `gorm:"text;not null"`
	Tags    []*Tag `gorm:"many2many:article_tag;constraint:OnDelete:CASCADE;"`
}
