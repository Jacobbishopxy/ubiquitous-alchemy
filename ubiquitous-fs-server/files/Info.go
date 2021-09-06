package files

import (
	"encoding/json"
	"io"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"path"
	"strings"
	"text/template"
	"time"

	"github.com/gorilla/mux"
)

type FileSystem struct {
	root string
}

func NewFileSystem(root string) FileSystem {
	return FileSystem{root: root}
}

type FileInfo struct {
	FileName string
	Size     int64
	Mode     uint32
	ModTime  time.Time
	FileType string
}

func getFileInfo(filepath string) (*FileInfo, error) {
	fileStat, err := os.Stat(filepath)

	if err != nil {
		return nil, err
	}

	var fileType string
	var isDir = fileStat.IsDir()
	if isDir {
		fileType = "folder"
	} else {
		fileType = strings.TrimPrefix(path.Ext(fileStat.Name()), ".")
	}

	return &FileInfo{
		FileName: fileStat.Name(),
		Size:     fileStat.Size(),
		Mode:     uint32(fileStat.Mode()),
		ModTime:  fileStat.ModTime(),
		FileType: fileType,
	}, nil
}

func (fs *FileSystem) DirectoryHandler(w http.ResponseWriter, r *http.Request) {
	fileInfos, err := listDir(fs.root)
	if err != nil {
		log.Printf("error[%s]\n", err)
		io.WriteString(w, err.Error())
		return
	}
	data, err := json.Marshal(fileInfos)
	if err != nil {
		log.Printf("convert JSON error[%s]\n", err)
		io.WriteString(w, err.Error())
		return
	}
	w.Write(data)
}

func (fs *FileSystem) DirectoryHandlerHtml(w http.ResponseWriter, r *http.Request) {
	fileInfos, err := listDir(fs.root)
	if err != nil {
		log.Printf("error[%s]\n", err)
		return
	}
	// analyze template
	tpl, err := template.ParseFiles("template/walker.html")
	if err != nil {
		log.Printf("error[%s]\n", err)
		return
	}
	// render
	data := &struct {
		CurrentPath  string
		RelativePath string
		FileInfos    []*FileInfo
	}{
		CurrentPath:  fs.root,
		RelativePath: "",
		FileInfos:    fileInfos,
	}
	err = tpl.Execute(w, data)
	if err != nil {
		log.Printf("error[%s]\n", err)
	}
}

// iter files
func (fs *FileSystem) FileWalkerHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	relativePath := strings.TrimSuffix(vars["relativePath"], "/")
	log.Printf("relativePath: %s\n", relativePath)
	fileInfos, err := listDir(fs.root + "/" + relativePath)
	if err != nil {
		log.Printf("error[%s]\n", err)
		io.WriteString(w, err.Error())
		return
	}
	data, err := json.Marshal(fileInfos)
	if err != nil {
		log.Printf("convert JSON error[%s]\n", err)
		io.WriteString(w, err.Error())
		return
	}
	w.Write(data)
}

func (fs *FileSystem) FileWalkerHandlerHtml(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	relativePath := strings.TrimSuffix(vars["relativePath"], "/")
	log.Printf("relativePath: %s\n", relativePath)
	currentPath := fs.root + "/" + relativePath
	fileInfos, err := listDir(currentPath)
	if err != nil {
		log.Printf("error[%s]\n", err)
		return
	}
	// analyze template
	tpl, err := template.ParseFiles("template/walker.html")
	if err != nil {
		log.Printf("error[%s]\n", err)
		return
	}
	// render
	data := &struct {
		CurrentPath  string
		RelativePath string
		FileInfos    []*FileInfo
	}{
		CurrentPath:  currentPath,
		RelativePath: relativePath,
		FileInfos:    fileInfos,
	}
	err = tpl.Execute(w, data)
	if err != nil {
		log.Printf("error[%s]\n", err)
	}
}

func (fs *FileSystem) DownloadFile(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	relativePath := strings.TrimSuffix(vars["relativePath"], "/")
	log.Printf("relativePath: %s\n", relativePath)
	currentPath := fs.root + "/" + relativePath
	fileInfo, err := os.Stat(currentPath)
	if err != nil {
		log.Printf("error[%s]\n", err)
		return
	}
	// directory
	if fileInfo.IsDir() {
		io.WriteString(w, "not directory")
		return
	}
	// file
	content, err := ioutil.ReadFile(currentPath)
	if err != nil {
		log.Printf("error[%s]\n", err)
		return
	}
	w.Write(content)
}
