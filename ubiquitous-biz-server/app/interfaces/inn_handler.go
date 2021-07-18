package interfaces

import (
	"fmt"
	"net/http"
	"strconv"
	"ubiquitous-biz-server/app/application"
	"ubiquitous-biz-server/app/domain/entity"
	"ubiquitous-biz-server/app/utils"

	"github.com/gin-gonic/gin"
)

type Inn struct {
	innApp application.InnAppInterface
}

func NewInn(innApp application.InnAppInterface) *Inn {
	return &Inn{innApp}
}

func (inn *Inn) SaveTag(c *gin.Context) {
	var saveTagError = make(map[string]string)
	var tag entity.Tag

	c.ShouldBindJSON(&tag)

	saveTagError = tag.Validate()
	if len(saveTagError) > 0 {
		utils.ErrorJSON(c, http.StatusBadRequest, saveTagError)
		return
	}

	t, err := inn.innApp.SaveTag(&tag)
	if err != nil {
		utils.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}

	utils.SuccessJSON(c, http.StatusOK, t)
}

func (inn *Inn) GetAllTag(c *gin.Context) {
	allTag, err := inn.innApp.GetAllTag()
	if err != nil {
		utils.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	utils.SuccessJSON(c, http.StatusOK, allTag)
}

func (inn *Inn) UpdateTag(c *gin.Context) {
	var updateTagError = make(map[string]string)
	var tag entity.Tag

	c.ShouldBindJSON(&tag)

	updateTagError = tag.Validate()
	if len(updateTagError) > 0 {
		utils.ErrorJSON(c, http.StatusBadRequest, updateTagError)
		return
	}

	t, err := inn.innApp.UpdateTag(&tag)
	if err != nil {
		utils.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}

	utils.SuccessJSON(c, http.StatusOK, t)
}

func (inn *Inn) DeleteTag(c *gin.Context) {
	idParam := c.Param("id")
	id, err := strconv.ParseUint(idParam, 10, 64)
	if err != nil {
		utils.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}
	err = inn.innApp.DeleteTag(id)
	if err != nil {
		utils.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	utils.SuccessJSON(c, http.StatusOK, fmt.Sprintf("tag %v deleted", id))
}

func (inn *Inn) SaveArticle(c *gin.Context) {
	var saveArticleError = make(map[string]string)
	var article entity.Article

	c.ShouldBindJSON(&article)

	saveArticleError = article.Validate("save")
	if len(saveArticleError) > 0 {
		utils.ErrorJSON(c, http.StatusBadRequest, saveArticleError)
		return
	}

	a, err := inn.innApp.SaveArticle(&article)
	if err != nil {
		utils.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}

	utils.SuccessJSON(c, http.StatusOK, a)
}

func (inn *Inn) GetAllArticle(c *gin.Context) {
	limitQuery := c.Query("limit")
	limit, err := strconv.ParseUint(limitQuery, 10, 64)
	if err != nil {
		utils.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}
	offsetQuery := c.Query("offset")
	offset, err := strconv.ParseUint(offsetQuery, 10, 64)
	if err != nil {
		utils.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}

	allArticle, err := inn.innApp.GetAllArticle(&entity.Pagination{Limit: limit, Offset: offset})
	if err != nil {
		utils.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	utils.SuccessJSON(c, http.StatusOK, allArticle)
}

func (inn *Inn) UpdateArticle(c *gin.Context) {
	var updateArticleError = make(map[string]string)
	var article entity.Article

	c.ShouldBindJSON(&article)

	updateArticleError = article.Validate("update")
	if len(updateArticleError) > 0 {
		utils.ErrorJSON(c, http.StatusBadRequest, updateArticleError)
		return
	}

	a, err := inn.innApp.UpdateArticle(&article)
	if err != nil {
		utils.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}

	utils.SuccessJSON(c, http.StatusOK, a)
}

func (inn *Inn) DeleteArticle(c *gin.Context) {
	idParam := c.Param("id")
	id, err := strconv.ParseUint(idParam, 10, 64)
	if err != nil {
		utils.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}
	err = inn.innApp.DeleteArticle(id)
	if err != nil {
		utils.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	utils.SuccessJSON(c, http.StatusOK, fmt.Sprintf("article %v deleted", id))
}
