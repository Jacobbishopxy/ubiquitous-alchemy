package persistence

import (
	"fmt"
	"ubiquitous-biz-server/app/domain/entity"
	"ubiquitous-biz-server/app/domain/repository"

	"github.com/jinzhu/gorm"
)

type Repositories struct {
	db *gorm.DB

	Inn repository.InnRepository
}

type RepositoriesConfig struct {
	DbDriver, DbUser, DbPassword, DbHost, DbPort, DbName string
}

func NewRepositories(config RepositoriesConfig) (*Repositories, error) {
	URI := fmt.Sprintf(
		"host=%s port=%s user=%s dbname=%s password=%s sslmode=disable",
		config.DbHost,
		config.DbPort,
		config.DbUser,
		config.DbName,
		config.DbPassword,
	)
	db, err := gorm.Open(config.DbDriver, URI)
	if err != nil {
		return nil, err
	}
	db.LogMode(true)

	return &Repositories{
		db:  db,
		Inn: NewInnRepository(db),
	}, nil
}

func (s *Repositories) Close() error {
	return s.db.Close()
}

func (s *Repositories) Automigrate() error {
	return s.db.AutoMigrate(&entity.Article{}, &entity.Tag{}).Error
}
