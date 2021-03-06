#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/secret.env
source ../../resources/go.env

export BACKEND_WEB_NAME=${BACKEND_WEB_NAME}
export BACKEND_WEB_VERSION=${BACKEND_WEB_VERSION}
export WEB_CONTAINER_NAME=${WEB_CONTAINER_NAME}
export WEB_CONTAINER_PORT=${WEB_CONTAINER_PORT}
export VOLUME_FRONTEND_EXT=${VOLUME_FRONTEND_EXT}
export VOLUME_FRONTEND_INN=${VOLUME_FRONTEND_INN}
export API_ADDRESS=${API_ADDRESS}
export AUTH_ADDRESS=${AUTH_ADDRESS}
export ASSET_MANAGEMENT_ADDRESS=${ASSET_MANAGEMENT_ADDRESS}
export RESOURCE_CENTRE_ADDRESS=${RESOURCE_CENTRE_ADDRESS}

docker-compose down
docker-compose -p "${PROJECT_NAME_WEB}" up -d
