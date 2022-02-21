#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/java.env
docker build \
		--target builder \
    -t "${AM_CACHE_NAME}":"${AM_CACHE_VERSION}" \
    --build-arg MAVEN_IMAGE_NAME="${MAVEN_IMAGE_NAME}" \
    --build-arg MAVEN_IMAGE_VERSION="${MAVEN_IMAGE_VERSION}" \
    --build-arg JAVA_BASE_NAME="${JAVA_BASE_NAME}" \
    --build-arg JAVA_BASE_VERSION="${JAVA_BASE_VERSION}" \
    -f ./Dockerfile ../..
