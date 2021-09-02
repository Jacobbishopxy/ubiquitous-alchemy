# Ubiquitous Viz Server

Ubiquitous viz server, as a web server supporting user interaction with backend, must work with ubiquitous alchemy server, who gives API supports.

## Requirements

`cp .env.template .env` and replace with your own configs.

## Dependencies

1. actix-cors: CORS support
1. actix-files: serving static files
1. actix-web: web framework
1. serde: JSON (de)serializer

## Project structure

1. [uv-backend](./uv-backend/src/lib.rs)

1. [uv-frontend](./uv-frontend/package.json)

## Notice

uv-frontend in `dev` mode: config `package.json` property `proxy` to the api-gateway host (eg: "http://localhost:8010").
