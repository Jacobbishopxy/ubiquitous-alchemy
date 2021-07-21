package persistence

import (
	"fmt"
	"ubiquitous-biz-server/app/domain/entity"
	"ubiquitous-biz-server/app/domain/repository"
	"ubiquitous-biz-server/app/util"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

type Repositories struct {
	db *gorm.DB

	InnRepo repository.Inn
}

type RepositoriesConfig struct {
	DbDriver, DbUser, DbPassword, DbHost, DbPort, DbName string
}

func NewRepositories(config RepositoriesConfig) (*Repositories, error) {
	URI := fmt.Sprintf(
		"host=%s port=%s user=%s password=%s dbname=%s sslmode=disable",
		config.DbHost,
		config.DbPort,
		config.DbUser,
		config.DbPassword,
		config.DbName,
	)

	db, err := gorm.Open(postgres.Open(URI))
	if err != nil {
		return nil, err
	}

	return &Repositories{
		db:      db,
		InnRepo: NewInnRepository(db),
	}, nil
}

func (s *Repositories) Close() error {
	d, err := s.db.DB()
	if err != nil {
		return util.NewBizError("Close connection failed")
	}
	return d.Close()
}

func (s *Repositories) Automigrate() error {
	return s.db.AutoMigrate(&entity.Article{}, &entity.Tag{})
}
