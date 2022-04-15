#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/java.env

export AS_BASE_NAME=${AS_BASE_NAME}
export AS_BASE_VERSION=${AS_BASE_VERSION}
export AS_API_VERSION=${AS_API_VERSION}
export AS_API_CONTAINER_NAME=${AS_API_CONTAINER_NAME}
export AS_API_CONTAINER_PORT=${AS_API_CONTAINER_PORT}
# temporary not using this
# export VOLUME_CONF_EXT=${VOLUME_CONF_EXT}
# export VOLUME_CONF_INN=${VOLUME_CONF_INN}
export VOLUME_LOG_EXT=${VOLUME_LOG_EXT}
export VOLUME_LOG_INN=${VOLUME_LOG_INN}

docker-compose down
docker-compose -p "${AS_API_NAME}" up -d
