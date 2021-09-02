#!/usr/bin/env bash
source ../../resources/auth.env

export APP_IMAGE_NAME=${APP_IMAGE_NAME}
export APP_IMAGE_VERSION=${APP_IMAGE_VERSION}
export CONTAINER_NAME=${CONTAINER_NAME}
export CONTAINER_PORT=${CONTAINER_PORT}
export VOLUME_CONF_EXT=${VOLUME_CONF_EXT}
export VOLUME_CONF_INN=${VOLUME_CONF_INN}
export ENV_PATH=${ENV_PATH}


# echo `ls $VOLUME_CONF_EXT`
# echo $VOLUME_CONF_INN
# echo $ENV_PATH

docker-compose down
docker-compose up 
