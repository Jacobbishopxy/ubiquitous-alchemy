# Ubiquitous Biz Server

## Requirements

`cp .env.template .env` and replace with your own configs.

## Project Architecture (DDD)

```txt
├── app
│   ├── adapters
│   │   └── persistence
│   │
│   ├── application
│   │
│   ├── domain
│   │   ├── entity
│   │   └── repository
│   │
│   ├── interfaces
│   │   ├── handlers
│   │   └── middleware
│   │
│   ├── util
│   │
│   └── server.go
│
├── cmd
│   └── app
│       └── main.go
│
├── .env
├── go.mod
├── go.dum
├── Makefile
└── README.md
```

- app

  - adapters: Interface Adapters. Implementation of domain repositories

    - persistence
    - store.go: integration of all persistence logic

  - application: Application business rules. Connection layer between adapters & interfaces, usually represents 'use cases'.

  - domain: Enterprise business rules.

    - entity: domain entity (database model)
    - repository: domain interfaces (abstract interfaces)

  - interfaces: Frameworks & Drivers

    - handlers: web router
    - middleware: http middleware

  - util: utilities

  - server.go: main function

- cmd
  - app/main.go: main application

![DDD](./DDD.jpg)

Dev path: domain -> application -> adapters -> interfaces
