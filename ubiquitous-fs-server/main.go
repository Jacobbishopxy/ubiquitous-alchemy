package main

import (
	"flag"
	"log"
	"net/http"
)

func main() {
	// Read arguments from command line
	port := flag.String("p", "8080", "port to serve on")
	directory := flag.String("d", ".", "the directory of static file to host")
	flag.Parse()

	// Create the fileServer Handler
	fileServer := http.FileServer(http.Dir(*directory))
	// Create a New Serve Mux to register handler
	mux := http.NewServeMux()
	mux.Handle("/", fileServer)

	// Create the server on Port 8080 and print start message!
	log.Printf("Serving %s on HTTP port: %s\n", *directory, *port)
	log.Fatal(http.ListenAndServe(":"+*port, mux))
}
