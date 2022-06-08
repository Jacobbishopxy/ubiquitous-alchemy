#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/java.env

export RC_BASE_NAME=${RC_BASE_NAME}
export RC_BASE_VERSION=${RC_BASE_VERSION}
export RC_API_VERSION=${RC_API_VERSION}
export RC_API_CONTAINER_NAME=${RC_API_CONTAINER_NAME}
export RC_API_CONTAINER_PORT=${RC_API_CONTAINER_PORT}
export VOLUME_LOG_EXT=${VOLUME_LOG_EXT}
export VOLUME_LOG_INN=${VOLUME_LOG_INN}

docker-compose down
docker-compose -p "${RC_API_NAME}" up -d
