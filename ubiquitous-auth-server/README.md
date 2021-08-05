# Ubiquitous Auth Server

## Configuration

- DATABASE_URL: data persistence (use Postgres with UUID extension)

- IS_SECURE: mailing server type -- false when no authentication

- SENDING_EMAIL_ADDRESS: email sender address

- SMTP_USERNAME: smtp username

- SMTP_PASSWORD: smtp password

- SMTP_HOST: smtp host

- SMTP_PORT: smtp port

- INVITATION_PAGE: invitation web page

- INVITATION_MESSAGE: invitation context

- SECRET_KEY: password hashing

- SECRET_LEN: password hashing

- PERSISTENCE_INIT: should create tables (migration)

- SERVICE_HOST: auth server host

- SERVICE_PORT: auth server port

- COOKIE_DURATION_SECS: cookie last for how long

## Testing

- Invitation

```sh
curl --request POST \
  --url http://localhost:8072/api/invitation \
  --header 'content-type: application/json' \
  --data '{"email":"name@domain.com"}'
```

- Registration

```sh
curl --request POST \
  --url http://localhost:8072/api/register/f87910d7-0e33-4ded-a8d8-2264800d1783 \
  --header 'content-type: application/json' \
  --data '{"nickname":"name", "email":"name@domain.com", "password":"password", "role":"editor"}'
```

ps: `f87910d7-0e33-4ded-a8d8-2264800d1783` is the Uuid created by Postgres, which is also the invitation id for registration

- Login

```sh
curl -i --request POST \
  --url http://localhost:8072/api/auth \
  --header 'content-type: application/json' \
  --data '{"email": "name@domain.com","password":"password"}'
```

```null
HTTP/1.1 200 OK
set-cookie: auth=iqsB4KUUjXUjnNRl1dVx9lKiRfH24itiNdJjTAJsU4CcaetPpaSWfrNq6IIoVR5+qKPEVTrUeg==; HttpOnly; Path=/; Domain=localhost; Max-Age=86400
content-length: 0
date: Sun, 28 Oct 2018 12:36:43 GMT
```

- Check alive

```sh
curl -i --request GET \
  --url http://localhost:8072/api/auth \
  --cookie auth=HdS0iPKTBL/4MpTmoUKQ5H7wft5kP7OjP6vbyd05Ex5flLvAkKd+P2GchG1jpvV6p9GQtzPEcg==
```

```null
HTTP/1.1 200 OK
content-length: 27
content-type: application/json
date: Sun, 28 Oct 2018 19:21:04 GMT

{"email":"name@domain.com"}
```

- Logout

```sh
curl -i --request DELETE \
  --url http://localhost:8072/api/auth
```

```null
HTTP/1.1 200 OK
set-cookie: auth=; HttpOnly; Path=/; Domain=localhost; Max-Age=0; Expires=Fri, 27 Oct 2017 13:01:52 GMT
content-length: 0
date: Sat, 27 Oct 2018 13:01:52 GMT
```
