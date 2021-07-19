package repository

import "ubiquitous-biz-server/app/domain/entity"

type InnRepository interface {
	SaveTag(*entity.Tag) (*entity.Tag, error)
	GetAllTag() ([]entity.Tag, error)
	UpdateTag(*entity.Tag) (*entity.Tag, error)
	DeleteTag(uint64) error

	SaveArticle(*entity.Article) (*entity.Article, error)
	GetAllArticle(*entity.Pagination) ([]entity.Article, error)
	UpdateArticle(*entity.Article) (*entity.Article, error)
	DeleteArticle(uint64) error
}
