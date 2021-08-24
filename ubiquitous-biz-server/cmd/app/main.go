package main

import (
	"ubiquitous-biz-server/app"
	"ubiquitous-biz-server/app/adapters/persistence"
	"ubiquitous-biz-server/app/application"
	"ubiquitous-biz-server/app/interfaces"
	"ubiquitous-biz-server/app/interfaces/middleware"
	"ubiquitous-biz-server/app/util"

	"github.com/gin-gonic/gin"
)

func main() {
	// Load config from file
	config, err := util.LoadConfig(".env")
	if err != nil {
		panic("config not found")
	}

	// init services
	services := initServices(*config)

	// init biz logic
	innApp := application.NewInnApp(services.InnRepo)
	innHdl := interfaces.NewInnHandler(*innApp)

	// setup web service
	r := gin.Default()
	r.Use(middleware.CORSMiddleware())

	// handlers registry
	server, err := app.NewServer(*config, innHdl)
	if err != nil {
		panic("Failed to build server")
	}

	// start server
	if err := server.Start(); err != nil {
		panic("Failed to start server")
	}
}

// services include database for persistence or external API connection
func initServices(config util.Config) *persistence.Store {
	services, err := persistence.NewStore(persistence.DatabaseConfig{
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
