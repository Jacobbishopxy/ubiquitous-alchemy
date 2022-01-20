# Ubiquitous Alchemy

This project serves as a collection of [Cyberbrick](https://github.com/Jacobbishopxy/cyberbrick) backend services.

1. [dev](./dev/README.md): development toolkit/ scripts

1. [docker](./docker/README.md): docker files

1. [resources](./resources/README.md): configuration files and etc.

1. [ubiquitous-api-gateway](./ubiquitous-api-gateway/README.md): API gateway

1. [abiquitous-auth-server](./abiquitous-auth-server/README.md): authentication

1. [ubiquitous-biz-server](./ubiquitous-biz-server/README.md): business logic implementation

1. [ubiquitous-data-server](./ubiquitous-data-server/README.md) data layer, data engine and data cache based on deps below:

   - [fabrix](https://github.com/Jacobbishopxy/fabrix): custom data structure, providing custom ETL interface and multiple data sources' adapters

1. [ubiquitous-fs-server](./ubiquitous-fs-server/README.md): file manage system

1. [ubiquitous-tg-server](./ubiquitous-tg-server/README.md): topological graph data server

1. [ubiquitous-viz-server](./ubiquitous-viz-server/README.md): visualization (components/demo) of ubiquitous-alchemy application

## Tech stack

### Language

1. Rust

   set rust tool chain to the nightly version:

   ```sh
   rustup default nightly
   ```

1. Golang

   current version: 1.17

1. Typescript

   current node version: 16.6.2
   current typescript version: 4.3.5

1. Python

   current version: 3.9

### Tools

- MySql

- Postgres

- Sqlite

- MongoDB

- Redis

- Docker
