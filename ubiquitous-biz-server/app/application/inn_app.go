package application

import (
	"ubiquitous-biz-server/app/domain/entity"
	"ubiquitous-biz-server/app/domain/repository"
)

type InnApp struct {
	ri repository.Inn
}

func NewInnApp(ib repository.Inn) *InnApp {
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
	return ia.ri.SaveTag(tag)
}

func (ia *InnApp) GetTag(id uint) (*entity.Tag, error) {
	return ia.ri.GetTag(id)
}

func (ia *InnApp) GetAllTag() ([]entity.Tag, error) {
	return ia.ri.GetAllTag()
}

func (ia *InnApp) UpdateTag(tag *entity.Tag) (*entity.Tag, error) {
	_, err := ia.ri.UpdateTag(tag)
	if err != nil {
		return nil, err
	}
	return ia.ri.GetTag(tag.Id)
}

func (ia *InnApp) DeleteTag(id uint) error {
	return ia.ri.DeleteTag(id)
}

func (ia *InnApp) SaveArticle(article *entity.Article) (*entity.Article, error) {
	return ia.ri.SaveArticle(article)
}

func (ia *InnApp) GetArticle(id uint) (*entity.Article, error) {
	return ia.ri.GetArticle(id)
}

func (ia *InnApp) GetAllArticle(pagination *entity.PaginationM10) ([]entity.Article, error) {
	return ia.ri.GetAllArticle(pagination)
}

func (ia *InnApp) UpdateArticle(article *entity.Article) (*entity.Article, error) {
	_, err := ia.ri.UpdateArticle(article)
	if err != nil {
		return nil, err
	}
	return ia.ri.GetArticle(article.Id)
}

func (ia *InnApp) DeleteArticle(id uint) error {
	return ia.ri.DeleteArticle(id)
}
