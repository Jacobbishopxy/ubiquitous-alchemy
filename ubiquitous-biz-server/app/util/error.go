package util

import "fmt"

type BizError struct {
	Err string `json:"err"`
}

func (be *BizError) Error() string {
	return fmt.Sprintf("biz error: %v", be.Err)
}

func NewBizError(err string) error {
	return &BizError{Err: err}
}
