#!/usr/bin/env bash
# author: Jacob Xie

source ../../resources/java.env
docker build \
    -t "${JAVA_BASE_NAME}":"${JAVA_BASE_VERSION}" \
    --build-arg JAVA_IMAGE_NAME="${JAVA_IMAGE_NAME}" \
    --build-arg JAVA_IMAGE_VERSION="${JAVA_IMAGE_VERSION}" \
    -f ./Dockerfile .
