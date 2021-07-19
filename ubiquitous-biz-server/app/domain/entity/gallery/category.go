package gallery

import (
	"time"
	"ubiquitous-biz-server/app/domain/entity"

	"gorm.io/datatypes"
)

type Category struct {
	entity.Common
	Name        string       `gorm:"size:256;not null;unique" json:"name" binding:"required"`
	Type        string       `gorm:"szie:100;not null" json:"type" binding:"required"`
	Description string       `json:"description,omitempty"`
	Symbols     []*Symbol    `gorm:"many2many:category_symbol" json:"symbols,omitempty"`
	Marks       []*Mark      `json:"marks,omitempty"`
	Dashboards  []*Dashboard `json:"dashboards,omitempty"`
	Contents    []*Content   `json:"contents,omitempty"`
}

type Symbol struct {
	entity.Common
	Name        string      `gorm:"size:256;not null;unique" json:"name" binding:"required"`
	Description string      `json:"description,omitempty"`
	Categories  []*Category `gorm:"many2many:category_symbol" json:"categories,omitempty"`
}

type Mark struct {
	entity.Common
	Name        string     `gorm:"size:256;not null" json:"name" binding:"required"`
	Description string     `json:"description,omitempty"`
	Category    Category   `json:"category,omitempty"`
	Contents    []*Content `gorm:"many2many:content_mark" json:"contents,omitempty"`
}

type Dashboard struct {
	entity.Common
	Name        string      `gorm:"size:256;not null" json:"name" binding:"required"`
	Description string      `json:"description,omitempty"`
	Templates   []*Template `json:"templates,omitempty"`
	Category    Category    `json:"category,omitempty"`
}

type Template struct {
	entity.Id
	Index       uint       `json:"index"`
	Name        string     `gorm:"size:256;not null" json:"name" binding:"required"`
	Description string     `json:"description,omitempty"`
	Elements    []*Element `json:"elements"`
}

// TODO: BeforeSave hook
type ElementType string

const (
	EmbedLink    ElementType = "embedLink"
	Text         ElementType = "text"
	Image        ElementType = "image"
	FileOverview ElementType = "fileOverview"
	FileManager  ElementType = "fileManager"
	XlsxTable    ElementType = "xlsxTable"
	FlexTable    ElementType = "flexTable"
	TargetPrice  ElementType = "targetPrice"
	Universe     ElementType = "universe"
	Line         ElementType = "line"
	Bar          ElementType = "bar"
	LineBar      ElementType = "lineBar"
	Pie          ElementType = "pie"
	Scatter      ElementType = "scatter"
	LineScatter  ElementType = "lineScatter"
	Heatmap      ElementType = "heatmap"
	Box          ElementType = "box"
	Tree         ElementType = "tree"
	TreeMap      ElementType = "treeMap"
)

type Element struct {
	entity.Id
	Name       string     `gorm:"size:256;not null" json:"name" binding:"required"`
	Type       string     `gorm:"size:256;not null" json:"type" binding:"required"`
	TimeSeries bool       `gorm:"default:false" json:"time_series"`
	X          uint       `gorm:"not null" json:"x" binding:"required"`
	Y          uint       `gorm:"not null" json:"y" binding:"required"`
	H          uint       `gorm:"not null" json:"h" binding:"required"`
	W          uint       `gorm:"not null" json:"w" binding:"required"`
	Template   Template   `json:"template"`
	Contents   []*Content `json:"contents,omitempty"`
}

type Content struct {
	entity.Common
	Date     time.Time          `grom:"default:CURRENT_TIMESTAMP" json:"time"`
	Title    string             `gorm:"size:256;not null" json:"title" binding:"required"`
	Data     datatypes.JSONMap  `json:"data"`
	Config   *datatypes.JSONMap `json:"config,omitempty"`
	Category *Category          `json:"category,omitempty"`
	Element  *Element           `json:"element,omitempty"`
	Symbol   *Symbol            `json:"symbol,omitempty"`
	Marks    []*Mark            `gorm:"many2many:content_mark" json:"marks,omitempty"`
}
