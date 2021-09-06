package files

import (
	"fmt"
	"log"
	"net/http"
	"os"
	"text/template"
)

func getHumanReadableSize(f os.FileInfo) string {
	if f.IsDir() {
		return "--"
	}
	bytes := f.Size()
	mb := float32(bytes) / (1024.0 * 1024.0)
	return fmt.Sprintf("%.2f MB", mb)
}

func getTemplateHTML() *template.Template {
	dirListTemplateHTMLPro, err := template.ParseFiles("static/template.html")
	if err != nil {
		panic("template.html not found!")
	}
	return dirListTemplateHTMLPro
}

func RequestLogger(handler http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		log.Printf("%s %s %s\n", r.RemoteAddr, r.Method, r.URL)
		handler.ServeHTTP(w, r)
	})
}
