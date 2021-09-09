# Ubiquitous Alchemy FS Server

File management system.

Studied from [this post](https://shockerli.net/post/golang-pkg-http-file-server/) and [blog](https://go.googlesource.com/proposal/+/master/design/draft-iofs.md).

## Dependency

- [afero](https://github.com/spf13/afero): A FileSystem Abstraction System for Go (file CRUD and permissions...)

## TODO

1. REST API
1. custom styles (default template)
1. custom template.html (pluggable)
1. file(s) upload
1. dynamic register file root (persistency)
1. tagging (file identifier: name/md5) and persistency
1. searching by tags
1. complete search engine
