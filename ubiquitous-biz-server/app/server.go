package app

import (
	"ubiquitous-biz-server/app/interfaces"
	"ubiquitous-biz-server/app/util"

	"github.com/gin-gonic/gin"
)

// server holds config & router info from web framework
// config: global variables, including database connection sql and etc.
// router: register API for external calls
type Server struct {
	config util.Config
	router *gin.Engine
}

// `Server` constructor
func NewServer(config util.Config, handlers ...interfaces.Handler) (*Server, error) {
	router := gin.Default()
	api := router.Group("/" + config.ApiPrefix)

	routerRegistry(api, handlers)

	server := Server{
		config: config,
		router: router,
	}

	return &server, nil

}

// register router
func routerRegistry(router *gin.RouterGroup, handlers []interfaces.Handler) {
	for _, h := range handlers {
		h.Register(router)
	}
}

// Start web application
func (s *Server) Start() error {
	return s.router.Run(":" + s.config.ApiPort)
}
