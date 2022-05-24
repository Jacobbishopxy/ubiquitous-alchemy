// proxyServer.go

package main

import (
	"flag"
	"fmt"
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
	"os"
	"path/filepath"
	"time"

	"github.com/gorilla/mux"
)

var apiAddress *string
var authAddress *string
var assetManagementAddress *string

func httpProxy(address *string) func(http.ResponseWriter, *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {
		url, err := url.Parse(*address)
		if err != nil {
			log.Println(err)
			return
		}

		proxy := httputil.NewSingleHostReverseProxy(url)
		proxy.ServeHTTP(w, r)
	}
}

func httpProxyStripPrefix(address *string, prefix string) func(http.ResponseWriter, *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {
		url, err := url.Parse(*address)
		if err != nil {
			log.Println(err)
			return
		}

		proxy := httputil.NewSingleHostReverseProxy(url)
		http.Handle(prefix, http.StripPrefix(prefix, proxy))
	}
}

// spaHandler implements the http.Handler interface, so we can use it
// to respond to HTTP requests. The path to the static directory and
// path to the index file within that static directory are used to
// serve the SPA in the given static directory.
type spaHandler struct {
	staticPath string
	indexPath  string
}

// ServeHTTP inspects the URL path to locate a file within the static dir
// on the SPA handler. If a file is found, it will be served. If not, the
// file located at the index path on the SPA handler will be served. This
// is suitable behavior for serving an SPA (single page application).
func (h spaHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	// get the absolute path to prevent directory traversal
	path, err := filepath.Abs(r.URL.Path)
	if err != nil {
		// if we failed to get the absolute path respond with a 400 bad request
		// and stop
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	// prepend the path with the path to the static directory
	path = filepath.Join(h.staticPath, path)

	// check whether a file exists at the given path
	_, err = os.Stat(path)
	if os.IsNotExist(err) {
		// file does not exist, serve index.html
		http.ServeFile(w, r, filepath.Join(h.staticPath, h.indexPath))
		return
	} else if err != nil {
		// if we got an error (that wasn't that the file doesn't exist) stating the
		// file, return a 500 internal server error and stop
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	// otherwise, use http.FileServer to serve the static dir
	http.FileServer(http.Dir(h.staticPath)).ServeHTTP(w, r)
}

func main() {
	// Arguments given by command line
	apiAddress = flag.String("a", "http://localhost:8030", "Address of the server-nodejs")
	authAddress = flag.String("u", "http://localhost:8061", "Address of the auth-service")
	assetManagementAddress = flag.String("m", "http://localhost:8060", "Address of the asset-management")
	staticPath := flag.String("p", "../frontend", "The path to the static directory")
	flag.Parse()

	// mux router
	router := mux.NewRouter()

	// wildcard to match url path
	router.HandleFunc("/api/{rest:.*}", httpProxy(apiAddress))
	router.HandleFunc("/auth/{rest:.*}", httpProxyStripPrefix(authAddress, "/auth"))
	router.HandleFunc("/v1/{rest:.*}", httpProxy(assetManagementAddress))
	// serve static HTML file
	spa := spaHandler{staticPath: *staticPath, indexPath: *staticPath + `/index.html`}
	router.PathPrefix("/").Handler(spa)

	srv := &http.Server{
		Handler: router,
		Addr:    "0.0.0.0:8000",
		// Good practice: enforce timeouts for servers you create!
		WriteTimeout: 15 * time.Second,
		ReadTimeout:  15 * time.Second,
	}
	fmt.Println("Serving at port 8000....")
	log.Fatal(srv.ListenAndServe())
}
