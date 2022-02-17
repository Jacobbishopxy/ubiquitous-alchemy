#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/java.env

export API_NAME=${API_NAME}
export API_VERSION=${API_VERSION}
export API_CONTAINER_NAME=${API_CONTAINER_NAME}
export API_CONTAINER_PORT=${API_CONTAINER_PORT}
export VOLUME_CONF_EXT=${VOLUME_CONF_EXT}
export VOLUME_CONF_INN=${VOLUME_CONF_INN}
export VOLUME_LOG_EXT=${VOLUME_LOG_EXT}
export VOLUME_LOG_INN=${VOLUME_LOG_INN}

docker-compose down
docker-compose -p "${PROJECT_NAME_API}" up -d
