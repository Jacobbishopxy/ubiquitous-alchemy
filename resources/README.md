# Ubiquitous Alchemy Resources

```txt
    .
    ├── asset-management
    │   ├── application.properties
    │   ├── log4j2.properties
    │   ├── persistence.properties
    │   └── persistence.template.properties
    ├── gateway.env
    ├── go.env
    ├── java.env
    ├── lura.env
    ├── nodejs.env
    ├── py.env
    ├── rust.env
    ├── secret.env
    ├── secret.template.env
    └── web.env
```

- `/asset-management`: resources for Java project **ubiquitous-asset-management**

  - `application.properties`
  - `log4j2.properties`: ignored by git since it is production-specific config.
  - `persistence.properties`: ignored by git since it is production-specific config.
  - `persistence.template.properties`: template for `persistence.properties`

- `gateway.env`: environment variables for the API gateway project **ubiquitous-api-gateway**.

- `go.env`: environment variables for general Golang projects.

- `java.env`: environment variables for general Java projects.

- `lura.env`: environment variables for **ubiquitous-api-gateway**.

- `nodejs.env`: environment variables for general NodeJS projects.

- `py.env`: environment variables for general Python projects.

- `rust.env`: environment variables for general Rust projects.

- `secret.env`: ignored by git since it is production-specific config (details see `secret.template.env`).

- `secret.template.env`: template for `secret.env`

- `web.env`: environment variables for the web application **server-web**.
