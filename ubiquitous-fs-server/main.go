package main

import (
	"flag"
	"log"
	"net/http"

	"github.com/gorilla/mux"

	"ubiquitous-fs-server/files"
)

func main() {
	// Read arguments from command line
	port := flag.String("p", "8080", "port to serve on")
	directory := flag.String("d", ".", "the directory of static file to host")
	flag.Parse()

	router := mux.NewRouter()
	fs := files.NewFileSystem(*directory)

	registerRoutes(&fs, router)

	run(router, *port)
}

func registerRoutes(fileSystem *files.FileSystem, router *mux.Router) {
	// static
	router.PathPrefix("/static/").Handler(http.StripPrefix("/", http.FileServer(http.Dir(""))))

	// json
	router.HandleFunc("/public", fileSystem.DirectoryHandler).
		Methods("GET").Headers("Content-Type", "application/json")
	router.PathPrefix("/public").
		Subrouter().HandleFunc("/{relativePath:[^\\s]*}", fileSystem.FileWalkerHandler).
		Methods("GET").Headers("Content-Type", "application/json")

	// html
	router.HandleFunc("/public", fileSystem.DirectoryHandlerHtml).
		Methods("GET")
	router.PathPrefix("/public").
		Subrouter().HandleFunc("/{relativePath:[^\\s]*}", fileSystem.FileWalkerHandlerHtml).
		Methods("GET")
	router.PathPrefix("/download").
		Subrouter().HandleFunc("/{relativePath:[^\\s]*}", fileSystem.DownloadFile).
		Methods("GET")
}

func run(router *mux.Router, port string) {
	// Routes consist of a path and a handler function.
	http.Handle("/", router)
	// Bind to a port and pass our router in
	log.Println("Service now running on " + port + "...")
	log.Fatal(http.ListenAndServe(":"+port, router))
}
