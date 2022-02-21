#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/java.env

export AM_BASE_NAME=${AM_BASE_NAME}
export AM_BASE_VERSION=${AM_BASE_VERSION}
export AM_API_VERSION=${AM_API_VERSION}
export AM_API_CONTAINER_NAME=${AM_API_CONTAINER_NAME}
export AM_API_CONTAINER_PORT=${AM_API_CONTAINER_PORT}
# temporary not using this
# export VOLUME_CONF_EXT=${VOLUME_CONF_EXT}
# export VOLUME_CONF_INN=${VOLUME_CONF_INN}
export VOLUME_LOG_EXT=${VOLUME_LOG_EXT}
export VOLUME_LOG_INN=${VOLUME_LOG_INN}

docker-compose down
docker-compose -p "${AM_API_NAME}" up -d
