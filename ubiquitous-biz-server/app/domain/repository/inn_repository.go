package repository

import "ubiquitous-biz-server/app/domain/entity"

type InnRepository interface {
	SaveTag(*entity.Tag) (*entity.Tag, map[string]string)
	GetAllTag() ([]entity.Tag, error)
	UpdateTag(*entity.Tag) (*entity.Tag, map[string]string)
	DeleteTag(uint64) error

	SaveArticle(*entity.Article) (*entity.Article, map[string]string)
	GetAllArticle(*entity.Pagination) ([]entity.Article, error)
	UpdateArticle(*entity.Article) (*entity.Article, map[string]string)
	DeleteArticle(uint64) error
}
