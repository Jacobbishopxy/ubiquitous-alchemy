package gallery

import "ubiquitous-biz-server/app/domain/entity"

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
	entity.UnitId
	Name       string `gorm:"size:256;not null"`
	Type       string `gorm:"size:256;not null"`
	TimeSeries bool   `gorm:"default:false"`
	X          uint   `gorm:"not null" json:"x"`
	Y          uint   `gorm:"not null" json:"y"`
	H          uint   `gorm:"not null" json:"h"`
	W          uint   `gorm:"not null" json:"w"`
	Template   Template
	Pointers   []*Pointer
}
