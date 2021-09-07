package main

import (
	"flag"
	"fmt"
	"log"
	"net/http"

	"ubiquitous-fs-server/files"
)

func main() {
	// Read arguments from command line
	port := flag.String("p", "8080", "port to serve on")
	directory := flag.String("d", ".", "the directory of static file to host")
	flag.Parse()

	// FileServer instance
	fs := files.NewFileSever(http.Dir(*directory))

	// Start FS server
	log.Printf("Serving %s on HTTP port: %s\n", *directory, *port)
	err := http.ListenAndServe(":"+*port, files.RequestLogger(fs))
	if err != nil {
		fmt.Println(err)
	}
}
