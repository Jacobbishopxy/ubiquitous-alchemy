package application

import (
	"ubiquitous-biz-server/app/domain/behavior"
	"ubiquitous-biz-server/app/domain/entity"
)

type InnApp struct {
	ib behavior.InnBehavior
}

func NewInnApp(ib behavior.InnBehavior) *InnApp {
	return &InnApp{ib}
}

var _ InnApplication = &InnApp{}

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

func (ia *InnApp) SaveTag(tag *entity.Tag) (*entity.Tag, error) {
	return ia.ib.SaveTag(tag)
}

func (ia *InnApp) GetTag(id uint) (*entity.Tag, error) {
	return ia.ib.GetTag(id)
}

func (ia *InnApp) GetAllTag() ([]entity.Tag, error) {
	return ia.ib.GetAllTag()
}

func (ia *InnApp) UpdateTag(tag *entity.Tag) (*entity.Tag, error) {
	_, err := ia.ib.UpdateTag(tag)
	if err != nil {
		return nil, err
	}
	return ia.ib.GetTag(tag.Id)
}

func (ia *InnApp) DeleteTag(id uint) error {
	return ia.ib.DeleteTag(id)
}

func (ia *InnApp) SaveArticle(article *entity.Article) (*entity.Article, error) {
	return ia.ib.SaveArticle(article)
}

func (ia *InnApp) GetArticle(id uint) (*entity.Article, error) {
	return ia.ib.GetArticle(id)
}

func (ia *InnApp) GetAllArticle(pagination *entity.PaginationM10) ([]entity.Article, error) {
	return ia.ib.GetAllArticle(pagination)
}

func (ia *InnApp) UpdateArticle(article *entity.Article) (*entity.Article, error) {
	_, err := ia.ib.UpdateArticle(article)
	if err != nil {
		return nil, err
	}
	return ia.ib.GetArticle(article.Id)
}

func (ia *InnApp) DeleteArticle(id uint) error {
	return ia.ib.DeleteArticle(id)
}
