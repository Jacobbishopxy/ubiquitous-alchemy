package interfaces

import (
	"github.com/gin-gonic/gin"
)

type UnitId struct {
	Id uint `json:"id" validate:"required"`
}

// Every handler should implements this interface
type Handler interface {
	// register API to Gin
	Register(router *gin.RouterGroup)
}
