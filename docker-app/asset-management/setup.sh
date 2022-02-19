#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/java.env
docker build \
		-t "${AM_BASE_NAME}":"${AM_BASE_VERSION}" \
    --build-arg MAVEN_BASE_NAME="${MAVEN_BASE_NAME}" \
    --build-arg MAVEN_BASE_VERSION="${MAVEN_BASE_VERSION}" \
    --build-arg JAVA_IMAGE_NAME="${JAVA_IMAGE_NAME}" \
    --build-arg JAVA_IMAGE_VERSION="${JAVA_IMAGE_VERSION}" \
    -f ./Dockerfile ../..
