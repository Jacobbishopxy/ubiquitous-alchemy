# UV Frontend

## Note

- `components/`: pure functional components, without any side-effect

- `pages/`: actual web pages, with side-effect

- `services/`: Http request & response, data model

- `App.tsx`: main page

- `index.tsx`: index page, wrapping `App.tsx` and providing global configs

## APIs

- [POST]`/api/cfg/conn`

  ```JSON
  {
      "name": "local PG12",
      "description": "local dev",
      "driver": "postgres",
      "username": "root",
      "password": "secret",
      "host": "localhost",
      "port": 5432,
      "database": "ubiquitous_alchemy"
  }
  ```

## TODO

1. auto check auth, if no login redirect to login page
1. Login & Registration APIs
