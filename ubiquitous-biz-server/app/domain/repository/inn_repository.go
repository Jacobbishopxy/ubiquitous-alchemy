package repository

import "ubiquitous-biz-server/app/domain/entity"

type Inn interface {
	SaveTag(*entity.Tag) (*entity.Tag, error)
	GetTag(uint) (*entity.Tag, error)
	GetAllTag() ([]entity.Tag, error)
	UpdateTag(*entity.Tag) (*entity.Tag, error)
	DeleteTag(uint) error

	SaveArticle(*entity.Article) (*entity.Article, error)
	GetArticle(uint) (*entity.Article, error)
	GetAllArticle(*entity.PaginationM10) ([]entity.Article, error)
	UpdateArticle(*entity.Article) (*entity.Article, error)
	DeleteArticle(uint) error
}
