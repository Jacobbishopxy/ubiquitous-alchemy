package main

import (
	"log"
	"os"
	"ubiquitous-biz-server/app/infrastructure/persistence"
	"ubiquitous-biz-server/app/interfaces"
	"ubiquitous-biz-server/app/interfaces/middleware"

	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
)

func init() {
	if err := godotenv.Load(); err != nil {
		log.Panicln("no env file found")
	}
}

func main() {
	dbDriver := os.Getenv("DB_DRIVER")
	host := os.Getenv("DB_HOST")
	password := os.Getenv("DB_PASSWORD")
	user := os.Getenv("DB_USER")
	dbName := os.Getenv("DB_NAME")
	port := os.Getenv("DB_PORT")

	services, err := persistence.NewRepositories(persistence.RepositoriesConfig{
		DbDriver:   dbDriver,
		DbHost:     host,
		DbPassword: password,
		DbUser:     user,
		DbName:     dbName,
		DbPort:     port,
	})
	if err != nil {
		panic(err)
	}
	defer services.Close()
	services.Automigrate()

	inn := interfaces.NewInn(services.Inn)

	r := gin.Default()
	r.Use(middleware.CORSMiddleware())

	api := r.Group("/api")

	groupInn := api.Group("/inn")

	groupInn.GET("/tag", inn.GetAllTag)
	groupInn.POST("/tag", inn.SaveTag)
	groupInn.PUT("/tag", inn.UpdateTag)
	groupInn.DELETE("/tag/:id", inn.DeleteTag)
	groupInn.GET("/article", inn.GetAllArticle)
	groupInn.POST("/article", inn.SaveArticle)
	groupInn.PUT("/article", inn.UpdateArticle)
	groupInn.DELETE("/article/:id", inn.DeleteArticle)

	app_port := os.Getenv("PORT")
	if app_port == "" {
		app_port = "8071"
	}
	log.Fatal(r.Run(":" + app_port))
}
