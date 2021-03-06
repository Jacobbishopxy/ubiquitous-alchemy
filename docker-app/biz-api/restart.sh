#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/nodejs.env
docker stop "${API_CONTAINER_NAME}"
docker rm "${API_CONTAINER_NAME}"
docker-compose -p "${PROJECT_NAME_API}" up -d
