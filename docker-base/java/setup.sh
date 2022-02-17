#!/usr/bin/env bash
# author: Jacob Xie

source ../../resources/java.env
docker build \
    -t "${BASE_IMAGE_NAME}":"${BASE_IMAGE_VERSION}" \
    --build-arg JAVA_IMAGE_NAME="${JAVA_IMAGE_NAME}" \
    --build-arg JAVA_IMAGE_VERSION="${JAVA_IMAGE_VERSION}" \
    -f ./Dockerfile .
