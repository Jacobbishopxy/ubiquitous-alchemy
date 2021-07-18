package persistence

import (
	"errors"
	"strings"
	"ubiquitous-biz-server/app/domain/entity"
	"ubiquitous-biz-server/app/domain/repository"

	_ "gorm.io/driver/postgres"
	"gorm.io/gorm"
)

type InnRepo struct {
	db *gorm.DB
}

func NewInnRepository(db *gorm.DB) *InnRepo {
	return &InnRepo{db}
}

var _ repository.InnRepository = &InnRepo{}

func (inn *InnRepo) SaveTag(tag *entity.Tag) (*entity.Tag, map[string]string) {
	dbErr := make(map[string]string)
	err := inn.db.Debug().Create(&tag).Error
	if err != nil {
		if strings.Contains(err.Error(), "duplicate") || strings.Contains(err.Error(), "Duplicate") {
			dbErr["unique_name"] = "tag name already taken"
			return nil, dbErr
		}
		dbErr["db_err"] = "database error"
		return nil, dbErr
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

func (inn *InnRepo) UpdateTag(tag *entity.Tag) (*entity.Tag, map[string]string) {
	dbErr := make(map[string]string)
	err := inn.db.Debug().Save(&tag).Error
	if err != nil {
		if strings.Contains(err.Error(), "duplicate") || strings.Contains(err.Error(), "Duplicate") {
			dbErr["unique_name"] = "tag name already taken"
			return nil, dbErr
		}
		dbErr["db_err"] = "database error"
		return nil, dbErr
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

func (inn *InnRepo) SaveArticle(article *entity.Article) (*entity.Article, map[string]string) {
	dbErr := make(map[string]string)
	err := inn.db.Debug().Create(&article).Error
	if err != nil {
		dbErr["db_err"] = "database error"
		return nil, dbErr
	}
	return article, nil
}

func (inn *InnRepo) GetAllArticle(pagination *entity.Pagination) ([]entity.Article, error) {
	var articles []entity.Article
	err := inn.db.Debug().
		Limit(int(pagination.Limit)).
		Offset(int(pagination.Offset)).
		Association("Tags").
		Find(&articles)
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, errors.New("article not found")
		}
		return nil, err
	}
	return articles, nil
}

func (inn *InnRepo) UpdateArticle(article *entity.Article) (*entity.Article, map[string]string) {
	dbErr := make(map[string]string)
	err := inn.db.Debug().Save(&article).Error
	if err != nil {
		dbErr["db_err"] = "database error"
		return nil, dbErr
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
