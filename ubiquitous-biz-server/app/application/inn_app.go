package application

import (
	"ubiquitous-biz-server/app/domain/behavior"
	"ubiquitous-biz-server/app/domain/entity"
)

type innApp struct {
	ib behavior.InnBehavior
}

var _ InnApplication = &innApp{}

type InnApplication interface {
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

func (ia *innApp) SaveTag(tag *entity.Tag) (*entity.Tag, error) {
	return ia.ib.SaveTag(tag)
}

func (ia *innApp) GetTag(id uint) (*entity.Tag, error) {
	return ia.ib.GetTag(id)
}

func (ia *innApp) GetAllTag() ([]entity.Tag, error) {
	return ia.ib.GetAllTag()
}

func (ia *innApp) UpdateTag(tag *entity.Tag) (*entity.Tag, error) {
	_, err := ia.ib.UpdateTag(tag)
	if err != nil {
		return nil, err
	}
	a, b := ia.ib.GetTag(tag.Id)
	return a, b
}

func (ia *innApp) DeleteTag(id uint) error {
	return ia.ib.DeleteTag(id)
}

func (ia *innApp) SaveArticle(article *entity.Article) (*entity.Article, error) {
	return ia.ib.SaveArticle(article)
}

func (ia *innApp) GetArticle(id uint) (*entity.Article, error) {
	return ia.ib.GetArticle(id)
}

func (ia *innApp) GetAllArticle(pagination *entity.PaginationM10) ([]entity.Article, error) {
	return ia.ib.GetAllArticle(pagination)
}

func (ia *innApp) UpdateArticle(article *entity.Article) (*entity.Article, error) {
	_, err := ia.ib.UpdateArticle(article)
	if err != nil {
		return nil, err
	}
	return ia.ib.GetArticle(article.Id)
}

func (ia *innApp) DeleteArticle(id uint) error {
	return ia.ib.DeleteArticle(id)
}
