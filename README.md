# Ubiquitous Alchemy

This project is a collection of [Cyberbrick](https://github.com/Jacobbishopxy/cyberbrick) services.

1. [dev](./dev/README.md): development toolkit
1. [ubiquitous-data-server](./ubiquitous-data-server/README.md) based on deps below:

   - [dyn-conn](https://github.com/Jacobbishopxy/rustopia): dynamic Sql connection, execution
   - [sqlz](https://github.com/Jacobbishopxy/rustopia): Sql AST model
   - [tiny-df](https://github.com/Jacobbishopxy/rustopia): Custom data structure, gluing third party crates
   - [xlz](https://github.com/Jacobbishopxy/rustopia): Xlsx toolkit

1. [ubiquitous-viz-server](./ubiquitous-viz-server/README.md): visualization of ubiquitous-data-server's application

1. [ubiquitous-api-gateway](./ubiquitous-api-gateway/README.md): API gateway

1. [ubiquitous-biz-server](./ubiquitous-biz-server/README.md): business logic implementation

1. [abiquitous-auth-server](./abiquitous-auth-server/README.md): authentication

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

### Tools

- MySql

- Postgres

- Sqlite

- MongoDB

- Redis

- Docker
