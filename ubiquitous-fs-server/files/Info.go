package files

import (
	"fmt"
	"log"
	"net/http"
	"net/url"
	"os"
	"path"
	"path/filepath"
	"sort"
	"strings"
	"text/template"
)

type FileServer struct {
	root http.FileSystem
}

func NewFileSever(root http.FileSystem) http.Handler {
	return &FileServer{root}
}

func (fs *FileServer) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	upath := r.URL.Path
	if !strings.HasPrefix(upath, "/") {
		upath = "/" + upath
		r.URL.Path = upath
	}

	ServeFile(w, r, fs.root, path.Clean(upath), true, "")
}

func ServeFile(
	w http.ResponseWriter,
	r *http.Request,
	fs http.FileSystem,
	name string,
	redirect bool,
	templateName string,
) {
	f, err := fs.Open(name)
	if err != nil {
		msg, code := toHTTPError(err)
		http.Error(w, msg, code)
		return
	}
	defer f.Close()

	d, err := f.Stat()
	if err != nil {
		msg, code := toHTTPError(err)
		http.Error(w, msg, code)
		return
	}

	if d.IsDir() {
		ListDirectory(w, r, f, templateName)
		return
	}
	http.ServeContent(w, r, d.Name(), d.ModTime(), f)
}

func toHTTPError(err error) (msg string, httpStatus int) {
	if os.IsNotExist(err) {
		return "404 page not found", http.StatusNotFound
	}
	if os.IsPermission(err) {
		return "403 Forbidden", http.StatusForbidden
	}
	// Default:
	return "500 Internal Server Error", http.StatusInternalServerError
}

func ListDirectory(w http.ResponseWriter, r *http.Request, f http.File, templateName string) {
	RootDir, err := f.Stat()
	if err != nil {
		panic(err)
	}
	var dirContents DirectoryInfo
	dirContents.DirName = RootDir.Name()
	dirContents.Files = make([]FileInfo, 0)
	dirs, err := f.Readdir(-1)
	if err != nil {
		log.Printf("http: error reading directory: %v", err)
		http.Error(w, "Error reading directory", http.StatusInternalServerError)
		return
	}
	sort.Slice(dirs, func(i, j int) bool { return dirs[i].Name() < dirs[j].Name() })
	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	for _, d := range dirs {
		name := d.Name()
		fileExtension := "page"
		if d.IsDir() {
			name += "/"
			fileExtension = "folder"
		} else if len(filepath.Ext(name)) > 1 {
			fileExtension = filepath.Ext(name)[1:]
		}

		url := url.URL{Path: name}
		fileContent := FileInfo{Name: name, Size: getHumanReadableSize(d), URL: url, Extension: fileExtension}
		dirContents.Files = append(dirContents.Files, fileContent)
	}
	dirContents.IPAddr = r.Host
	renderTemplate(w, templateName, dirContents)
}

func renderTemplate(w http.ResponseWriter, tmpl string, data interface{}) {
	var t *template.Template
	var err error
	// use default rendering html
	if len(tmpl) == 0 {
		t = getTemplateHTML()
	} else {
		templatePath, _ := filepath.Abs(tmpl + ".html")
		fmt.Println("template path", templatePath)
		t, err = template.ParseFiles(templatePath)
	}

	if err != nil {
		fmt.Println("Error in parsing template ", err)
		panic(err)
	}
	t.Execute(w, data)
}

type DirectoryInfo struct {
	DirName string
	Files   []FileInfo
	IPAddr  string
}

type FileInfo struct {
	Name      string
	Size      string
	URL       url.URL
	Extension string
}
