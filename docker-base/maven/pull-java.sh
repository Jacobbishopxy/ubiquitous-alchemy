#!/usr/bin/env bash
# author: Jacob Xie

source ../../resources/java.env
docker pull "${JAVA_IMAGE_NAME}":"${JAVA_IMAGE_VERSION}"
