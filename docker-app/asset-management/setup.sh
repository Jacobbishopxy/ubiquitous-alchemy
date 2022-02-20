#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/java.env
docker build \
		-t "${AM_BASE_NAME}":"${AM_BASE_VERSION}" \
    --build-arg MAVEN_IMAGE_NAME="${MAVEN_IMAGE_NAME}" \
    --build-arg MAVEN_IMAGE_VERSION="${MAVEN_IMAGE_VERSION}" \
    --build-arg JAVA_BASE_NAME="${JAVA_BASE_NAME}" \
    --build-arg JAVA_BASE_VERSION="${JAVA_BASE_VERSION}" \
    -f ./Dockerfile ../..
