#!/usr/bin/env bash
# author: Jacob Xie

source ../../resources/java.env
docker build \
    -t "${MAVEN_BASE_NAME}":"${MAVEN_BASE_VERSION}" \
    --build-arg MAVEN_IMAGE_NAME="${MAVEN_IMAGE_NAME}" \
    --build-arg MAVEN_IMAGE_VERSION="${MAVEN_IMAGE_VERSION}" \
    -f ./Dockerfile .
