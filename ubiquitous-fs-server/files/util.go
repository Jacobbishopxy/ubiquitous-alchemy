package files

import (
	"fmt"
	"log"
	"net/http"
	"os"
	"syscall"
	"text/template"
	"time"
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

func statTimes(name string) (atime, mtime, ctime time.Time, err error) {
	fi, err := os.Stat(name)
	if err != nil {
		return
	}
	mtime = fi.ModTime()
	stat := fi.Sys().(*syscall.Stat_t)
	atime = time.Unix(int64(stat.Atim.Sec), int64(stat.Atim.Nsec))
	ctime = time.Unix(int64(stat.Ctim.Sec), int64(stat.Ctim.Nsec))
	return
}

func time2string(t time.Time, defaultString string, format string) string {
	if t.IsZero() {
		return defaultString
	}
	return t.Format(format)
}

func RequestLogger(handler http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		log.Printf("%s %s %s\n", r.RemoteAddr, r.Method, r.URL)
		handler.ServeHTTP(w, r)
	})
}
