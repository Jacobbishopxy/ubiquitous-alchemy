package interfaces

type newTag struct {
	Name        string `json:"name" validate:"required,gt=1"`
	Description string `json:"description,omitempty" validate:"omitempty,gt=1"`
	Color       string `json:"color,omitempty" validate:"omitempty,iscolor"`
}

type updateTag struct {
	UnitId
	Name        string `json:"name,omitempty" validate:"omitempty,gt=1"`
	Description string `json:"description,omitempty" validate:"omitempty,gt=1"`
	Color       string `json:"color,omitempty" validate:"omitempty,iscolor"`
}

type newArticle struct {
	Title   string    `json:"title" validate:"required,gt=1"`
	Content string    `json:"content" validate:"required,gt=1"`
	Tags    []*UnitId `json:"tags"`
}

type UpdateArticle struct {
	UnitId
	Title   string    `json:"title,omitempty" validate:"omitempty,gt=1"`
	Content string    `json:"content,omitempty" validate:"omitempty,gt=1"`
	Tags    []*UnitId `json:"tags"`
}
