package persistence

import (
	"errors"
	"ubiquitous-biz-server/app/domain/entity"
	"ubiquitous-biz-server/app/domain/repository"
	"ubiquitous-biz-server/app/util"

	"gorm.io/gorm"
	"gorm.io/gorm/clause"
)

type InnRepo struct {
	db *gorm.DB
}

func NewInnRepository(db *gorm.DB) *InnRepo {
	return &InnRepo{db}
}

// Implementation of domian model
var _ repository.Inn = &InnRepo{}

func (inn *InnRepo) SaveTag(tag *entity.Tag) (*entity.Tag, error) {
	err := inn.db.Debug().Create(&tag).Error
	if err != nil {
		return nil, err
	}
	return tag, nil
}

func (inn *InnRepo) GetTag(id uint) (*entity.Tag, error) {
	var tag entity.Tag
	err := inn.db.Debug().First(&tag, id).Error
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, util.NewBizError(err.Error())
		}
		return nil, util.NewBizError(err.Error())
	}
	return &tag, nil
}

func (inn *InnRepo) GetAllTag() ([]entity.Tag, error) {
	var tags []entity.Tag
	err := inn.db.Debug().Find(&tags).Error
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, util.NewBizError("tag not found")

		}
		return nil, err
	}
	return tags, nil
}

func (inn *InnRepo) UpdateTag(tag *entity.Tag) (*entity.Tag, error) {
	err := inn.db.Debug().Updates(&tag).Error
	if err != nil {
		return nil, err
	}
	return tag, nil
}

func (inn *InnRepo) DeleteTag(id uint) error {
	err := inn.db.Debug().Delete(&entity.Tag{}, id).Error
	if err != nil {
		return util.NewBizError(err.Error())
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

func (inn *InnRepo) GetArticle(id uint) (*entity.Article, error) {
	var article entity.Article
	err := inn.db.Debug().
		Preload("Tags").
		First(&article, id).
		Error
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, util.NewBizError(err.Error())
		}
		return nil, util.NewBizError(err.Error())
	}
	return &article, nil
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
			return nil, util.NewBizError("article not found")
		}
		return nil, err
	}
	return articles, nil
}

func (inn *InnRepo) UpdateArticle(article *entity.Article) (*entity.Article, error) {
	err := inn.db.Debug().Updates(&article).Error
	if err != nil {
		return nil, err
	}
	return article, nil
}

func (inn *InnRepo) DeleteArticle(id uint) error {
	err := inn.db.Debug().Delete(&entity.Article{}, id).Error
	if err != nil {
		return util.NewBizError(err.Error())
	}
	return nil
}
