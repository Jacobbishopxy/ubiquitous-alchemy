ifneq (,$(wildcard ./.env))
    include .env
    export
endif

dev:
	go run ./cmd/app

.PHONY: dev
