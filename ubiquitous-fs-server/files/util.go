package files

import (
	"errors"
	"io/ioutil"
	"log"
	"os"
)

func listDir(dir string) ([]*FileInfo, error) {
	fileInfo, err := os.Stat(dir)
	if err != nil {
		log.Printf("error[%s]\n", err)
		return nil, err
	}
	// directory
	if fileInfo.IsDir() {
		return processDir(dir)
	}
	// file
	return nil, errors.New("not directory")
}

func processDir(dir string) ([]*FileInfo, error) {
	list, err := ioutil.ReadDir(dir)
	if err != nil {
		log.Printf("error[%s]\n", err)
		return nil, err
	}
	fileInfos := make([]*FileInfo, 0)
	for _, file := range list {
		fileInfo, err := getFileInfo(file.Name())
		if err == nil {
			fileInfos = append(fileInfos, fileInfo)
		}
	}
	return fileInfos, nil
}
