ifneq (,$(wildcard ./dev.env))
    include dev.env
    export
endif

network-init:
	docker network create ${NETWORK}

listen:
	lsof -i -P -n | grep LISTEN

# =============================================================================
# Postgres
# =============================================================================

pg-init:
	docker run --network=${NETWORK} --name postgres12 -p 5432:5432 -e POSTGRES_USER=${USER} -e POSTGRES_PASSWORD=${PASS} -d postgres:12-alpine

pg-evoke:
	docker start postgres12

pg-createdb:
	docker exec -it postgres12 createdb --username=${USER} --owner=${USER} ${DB}

pg-uuid:
	docker exec -i postgres12 psql -U ${USER} -d ${DB} -c 'create extension if not exists "uuid-ossp"'

pg-dropdb:
	docker exec -it postgres12 dropdb ${DB}

# =============================================================================
# MySQL
# =============================================================================

my-init:
	docker run --network=${NETWORK} --name mysql8 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=${PASS} -d mysql:8

my-evoke:
	docker start mysql8

my-createdb:
	docker exec -it mysql8 mysql -uroot -p${PASS} -e "create database ${DB};"

my-dropdb:
	docker exec -it mysql8 mysql -uroot -p${PASS} -e "drop database ${DB};"

# =============================================================================
# Redis
# =============================================================================

redis-init:
	docker run --network=${NETWORK} --name redis6 -p 6379:6379 -d redis:6 --requirepass "${PASS}"

# =============================================================================
# MongoDB
# =============================================================================

mongo-init:
	docker run --network=${NETWORK} --name mongo -p 27017:27017 -d -e MONGO_INITDB_ROOT_USERNAME=${USER} -e MONGO_INITDB_ROOT_PASSWORD=${PASS} mongo

.PHONY: listen pg-init pg-evoke pg-uuid pg-createdb pg-dropdb my-init my-evoke my-createdb my-dropdb redis-init mongo-init
