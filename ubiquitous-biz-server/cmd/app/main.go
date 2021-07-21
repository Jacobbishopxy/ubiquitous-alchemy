package main

import (
	"log"
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

	innApp := application.NewInnApp(services.InnBehavior)
	inn := interfaces.NewInn(*innApp)

	r := gin.Default()
	r.Use(middleware.CORSMiddleware())

	api := r.Group("/api")

	groupInn := api.Group("/inn")

	groupInn.GET("/tags", inn.GetAllTag)
	groupInn.GET("/tag/:id", inn.GetTag)
	groupInn.POST("/tag", inn.SaveTag)
	groupInn.PUT("/tag", inn.UpdateTag)
	groupInn.DELETE("/tag/:id", inn.DeleteTag)
	groupInn.GET("/articles", inn.GetAllArticle)
	groupInn.GET("/article/:id", inn.GetArticle)
	groupInn.POST("/article", inn.SaveArticle)
	groupInn.PUT("/article", inn.UpdateArticle)
	groupInn.DELETE("/article/:id", inn.DeleteArticle)

	log.Fatal(r.Run(":" + config.ApiPort))
}
