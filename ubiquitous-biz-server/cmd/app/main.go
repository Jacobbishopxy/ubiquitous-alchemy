package main

import (
	"ubiquitous-biz-server/app"
	"ubiquitous-biz-server/app/application"
	"ubiquitous-biz-server/app/infrastructure/persistence"
	"ubiquitous-biz-server/app/interfaces"
	"ubiquitous-biz-server/app/interfaces/middleware"
	"ubiquitous-biz-server/app/util"

	"github.com/gin-gonic/gin"
)

func main() {
	config, err := util.LoadConfig(".env")
	if err != nil {
		panic("config not found")
	}

	services := initServices(*config)

	innApp := application.NewInnApp(services.InnRepo)
	innHdl := interfaces.NewInnHandler(*innApp)

	r := gin.Default()
	r.Use(middleware.CORSMiddleware())

	server, err := app.NewServer(*config, innHdl)
	if err != nil {
		panic("Failed to build server")
	}

	if err := server.Start(); err != nil {
		panic("Failed to start server")
	}
}

func initServices(config util.Config) *persistence.Repositories {
	services, err := persistence.NewRepositories(persistence.RepositoriesConfig{
		DbDriver:   config.DbDriver,
		DbHost:     config.DbHost,
		DbPassword: config.DbPassword,
		DbUser:     config.DbUser,
		DbName:     config.DbName,
		DbPort:     config.DbPort,
	})
	if err != nil {
		panic(err)
	}
	defer services.Close()
	services.Automigrate()

	return services
}
