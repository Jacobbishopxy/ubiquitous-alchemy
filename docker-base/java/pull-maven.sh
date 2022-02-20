#!/usr/bin/env bash
# author: Jacob Xie

source ../../resources/java.env
docker pull "${MAVEN_IMAGE_NAME}":"${MAVEN_IMAGE_VERSION}"
