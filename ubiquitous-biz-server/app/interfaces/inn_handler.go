package interfaces

import (
	"fmt"
	"net/http"
	"strconv"
	"ubiquitous-biz-server/app/application"
	"ubiquitous-biz-server/app/domain/entity"
	"ubiquitous-biz-server/app/util"

	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
	"github.com/jinzhu/copier"
)

type Inn struct {
	validate *validator.Validate
	innApp   application.InnApp
}

func NewInn(innApp application.InnApp) *Inn {
	validate := validator.New()
	return &Inn{validate, innApp}
}

func (inn *Inn) SaveTag(c *gin.Context) {
	var saveTagError error
	var tag newTag

	c.ShouldBindJSON(&tag)

	saveTagError = inn.validate.Struct(tag)
	if saveTagError != nil {
		util.ErrorJSON(c, http.StatusBadRequest, saveTagError)
		return
	}

	st := entity.Tag{}
	st.Name = tag.Name
	st.Description = tag.Description
	st.Color = tag.Color

	t, err := inn.innApp.SaveTag(&st)
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}

	util.SuccessJSON(c, http.StatusOK, t)
}

func (inn *Inn) GetTag(c *gin.Context) {
	idParam := c.Param("id")
	id, err := strconv.ParseUint(idParam, 10, 64)
	if err != nil {
		util.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}
	tag, err := inn.innApp.GetTag(uint(id))
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	util.SuccessJSON(c, http.StatusOK, tag)
}

func (inn *Inn) GetAllTag(c *gin.Context) {
	allTag, err := inn.innApp.GetAllTag()
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	util.SuccessJSON(c, http.StatusOK, allTag)
}

func (inn *Inn) UpdateTag(c *gin.Context) {
	var updateTagError error
	var tag updateTag

	c.ShouldBindJSON(&tag)

	updateTagError = inn.validate.Struct(tag)
	if updateTagError != nil {
		util.ErrorJSON(c, http.StatusBadRequest, updateTagError)
		return
	}

	ut := new(entity.Tag)
	copier.Copy(ut, tag)

	t, err := inn.innApp.UpdateTag(ut)
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}

	util.SuccessJSON(c, http.StatusOK, t)
}

func (inn *Inn) DeleteTag(c *gin.Context) {
	idParam := c.Param("id")
	id, err := strconv.ParseUint(idParam, 10, 64)
	if err != nil {
		util.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}
	err = inn.innApp.DeleteTag(uint(id))
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	util.SuccessJSON(c, http.StatusOK, fmt.Sprintf("tag %v deleted", id))
}

func (inn *Inn) SaveArticle(c *gin.Context) {
	var saveArticleError error
	var article newArticle

	c.ShouldBindJSON(&article)

	saveArticleError = inn.validate.Struct(article)
	if saveArticleError != nil {
		util.ErrorJSON(c, http.StatusBadRequest, saveArticleError)
		return
	}

	sa := new(entity.Article)
	copier.Copy(sa, article)
	for _, t := range article.Tags {
		if saveArticleError = inn.validate.Struct(t); saveArticleError != nil {
			util.ErrorJSON(c, http.StatusBadRequest, saveArticleError)
			return
		}
		et := new(entity.Tag)
		et.Id = t.Id
		sa.Tags = append(sa.Tags, et)
	}

	a, err := inn.innApp.SaveArticle(sa)
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}

	util.SuccessJSON(c, http.StatusOK, a)
}

func (inn *Inn) GetArticle(c *gin.Context) {
	idParam := c.Param("id")
	id, err := strconv.ParseUint(idParam, 10, 64)
	if err != nil {
		util.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}
	article, err := inn.innApp.GetArticle(uint(id))
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	util.SuccessJSON(c, http.StatusOK, article)
}

func (inn *Inn) GetAllArticle(c *gin.Context) {
	limitQuery := c.Query("limit")
	limit, err := strconv.ParseUint(limitQuery, 10, 64)
	if err != nil {
		util.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}
	offsetQuery := c.Query("offset")
	offset, err := strconv.ParseUint(offsetQuery, 10, 64)
	if err != nil {
		util.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}

	allArticle, err := inn.innApp.GetAllArticle(
		&entity.PaginationM10{Limit: uint(limit), Offset: uint(offset)},
	)
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	util.SuccessJSON(c, http.StatusOK, allArticle)
}

func (inn *Inn) UpdateArticle(c *gin.Context) {
	var updateArticleError error
	var article UpdateArticle

	c.ShouldBindJSON(&article)

	updateArticleError = inn.validate.Struct(article)
	if updateArticleError != nil {
		util.ErrorJSON(c, http.StatusBadRequest, updateArticleError)
		return
	}

	ua := new(entity.Article)
	copier.Copy(ua, article)
	for _, t := range article.Tags {
		if updateArticleError = inn.validate.Struct(t); updateArticleError != nil {
			util.ErrorJSON(c, http.StatusBadRequest, updateArticleError)
		}
		et := new(entity.Tag)
		et.Id = t.Id
		ua.Tags = append(ua.Tags, et)
	}

	a, err := inn.innApp.UpdateArticle(ua)
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}

	util.SuccessJSON(c, http.StatusOK, a)
}

func (inn *Inn) DeleteArticle(c *gin.Context) {
	idParam := c.Param("id")
	id, err := strconv.ParseUint(idParam, 10, 64)
	if err != nil {
		util.ErrorJSON(c, http.StatusBadRequest, err)
		return
	}
	err = inn.innApp.DeleteArticle(uint(id))
	if err != nil {
		util.ErrorJSON(c, http.StatusInternalServerError, err)
		return
	}
	util.SuccessJSON(c, http.StatusOK, fmt.Sprintf("article %v deleted", id))
}
