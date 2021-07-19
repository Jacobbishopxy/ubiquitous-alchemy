package persistence

import (
	"errors"
	"ubiquitous-biz-server/app/domain/entity"
	"ubiquitous-biz-server/app/domain/repository"

	"gorm.io/gorm"
	"gorm.io/gorm/clause"
)

type InnRepo struct {
	db *gorm.DB
}

func NewInnRepository(db *gorm.DB) *InnRepo {
	return &InnRepo{db}
}

var _ repository.InnRepository = &InnRepo{}

func (inn *InnRepo) SaveTag(tag *entity.Tag) (*entity.Tag, error) {
	err := inn.db.Debug().Create(&tag).Error
	if err != nil {
		return nil, err
	}
	return tag, nil
}

func (inn *InnRepo) GetAllTag() ([]entity.Tag, error) {
	var tags []entity.Tag
	err := inn.db.Debug().Find(&tags).Error
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, errors.New("tag not found")

		}
		return nil, err
	}
	return tags, nil
}

func (inn *InnRepo) UpdateTag(tag *entity.Tag) (*entity.Tag, error) {
	err := inn.db.Debug().Save(&tag).Error
	if err != nil {
		return nil, err
	}
	return tag, nil
}

func (inn *InnRepo) DeleteTag(id uint64) error {
	var tag entity.Tag
	err := inn.db.Debug().Where("id = ?", id).Association("Articles").Delete(&tag).Error
	if err != nil {
		return errors.New("delete error")
	}
	return nil
}

func (inn *InnRepo) SaveArticle(article *entity.Article) (*entity.Article, error) {
	err := inn.db.Debug().Create(&article).Error
	if err != nil {
		return nil, err
	}
	return article, nil
}

func (inn *InnRepo) GetAllArticle(pagination *entity.PaginationM10) ([]entity.Article, error) {
	var articles []entity.Article
	err := inn.db.Debug().
		Preload("Tags").
		Preload(clause.Associations).
		Limit(int(pagination.Limit)).
		Offset(int(pagination.Offset)).
		Find(&articles).
		Error

	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, errors.New("article not found")
		}
		return nil, err
	}
	return articles, nil
}

func (inn *InnRepo) UpdateArticle(article *entity.Article) (*entity.Article, error) {
	err := inn.db.Debug().Save(&article).Error
	if err != nil {
		return nil, err
	}
	return article, nil
}

func (inn *InnRepo) DeleteArticle(id uint64) error {
	var article entity.Article
	err := inn.db.Debug().Where("id = ?", id).Association("Tags").Delete(&article).Error
	if err != nil {
		return errors.New("delete error")
	}
	return nil
}
