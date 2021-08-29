# Ubiquitous Alchemy 

## Why Ubiquitous Alchemy?
Ubiquitous Alchemy provides different services for the [Cyberbrick](https://github.com/Jacobbishopxy/cyberbrick) project such as authentification service and ETL service. Those services have separated logic from each other and the front-end. If all are integrated with a single service, a minor update would affect the whole service. To better manage different services, we put them as different microservices. In this case, refactoring one microservice wouldn't affect other running microservices. 

## Cyberbrick Project Structure

TODO

## ubiquitous-api-gateway

An API gateway sits between clients and services. It acts as a reverse proxy, routing requests from clients to services. It may also perform various cross-cutting tasks such as authentication and data persistence. Without a gateway, clients must send requests directly to front-end services. This may result in complex client code and coupling client and backend code. A gateway helps to address these issues by decoupling clients from services. [source](https://docs.microsoft.com/en-us/azure/architecture/microservices/design/gateway)

ubiquitous-api-gateway is based on [lura](https://github.com/luraproject/lura), an open framework to assemble ultra performance API Gateways with middlewares; formerly known as KrakenD framework. 

ubiquitous-api-gateway reads a [KrakenD's configuration file](https://www.krakend.io/docs/configuration/overview/). The configuration file needs to be a JSON file. The main structure of the config file is:
```json
    {
        "version": 2,
        "endpoints": [],
        "extra_config": {}
    }
```
`version`: The version of the KrakenD file format.
Version 2: current version
Version 1: Deprecated in 2016, for version v0.3.9 and older.
`endpoints[]`: An array of endpoint objects offered by the gateway and all the associated backends and configurations. This is your API definition.
`extra_config{}`: Components' configuration. Whatever is not a core functionality of the Lura Project is declared in a unique namespace in the configuration, so that you can configure multiple elements without collisions.

In production mode, ubiquitous-api-gateway is a binary executable file. It takes in the following flags as arguments:
-p  "Port of the service"
-l  "Logging level"
-d  "Enable the debug"
-c  "Path to the configuration filename"

## ubiquitous-auth-server

ubiquitous-auth-server provides an authentification service that could handle user registration, login, and logout. 

Its [api](./../ubiquitous-auth-server/README.md) includes taking user's information for registration, sending an email invitation, handling invitation's confirmation, allowing registered users to log in, and logout.

1. Invitation
   The invitation request data is of the form:
```rust
   pub struct InvitationReq {
    pub nickname: String,
    pub email: String,
    pub password: String,
}
```
When receives an invitation request, auth-server first hashes the user password with `util/encription_helper.rs`. It then creates a new `Invitation` instance, with the user role default to 'visitor', and persists the invitation to the database. It then sends the invitation email through an `emailService` instance. The invitation email contains the invitation id as a parameter.

2. Registration
   A user will only be registered after this API. When a client sends a request with invitation id as a parameter, auth-server will confirm a user's invitation id & email, and then register the user. To confirm the invitation, auth server checks whether the invitation is in the database and whether the invitation is expired. In case of a user sends several invitation request, auth-server will only consider the latest request. If the invitation is valid, copy user registration info from the `invitation` table and save it to the `user` table. After saving information to the `user` info, the whole registration process finishes.

3. Login & Logout
   auth-server handles user login and logout with cookies. 
   1. When receives a login request, the auth-server leaves the client a cookie that contains the user's info. It first checks whether the user exists in the user table. If not, it verifies the password using `encryption_helper.rs` (notice that the password in the database is hashed while the password received from the client is not). If the password is valid, serializing user info to string and setting cookies.
   2. To prevent unnecessary login, auth-server also provides an api to check whether the user is logged in with cookies.
   3. To log a user out, simply "delete" the cookies.
