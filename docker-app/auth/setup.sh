#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/java.env

# name builder image
docker build \
		--target builder \
    -t "${AS_CACHE_NAME}":"${AS_BASE_VERSION}" \
    --build-arg MAVEN_IMAGE_NAME="${MAVEN_IMAGE_NAME}" \
    --build-arg MAVEN_IMAGE_VERSION="${MAVEN_IMAGE_VERSION}" \
    --build-arg JAVA_BASE_NAME="${JAVA_BASE_NAME}" \
    --build-arg JAVA_BASE_VERSION="${JAVA_BASE_VERSION}" \
    -f ./Dockerfile ../..

# build app image
docker build \
		-t "${AS_BASE_NAME}":"${AS_BASE_VERSION}" \
    --build-arg MAVEN_IMAGE_NAME="${MAVEN_IMAGE_NAME}" \
    --build-arg MAVEN_IMAGE_VERSION="${MAVEN_IMAGE_VERSION}" \
    --build-arg JAVA_BASE_NAME="${JAVA_BASE_NAME}" \
    --build-arg JAVA_BASE_VERSION="${JAVA_BASE_VERSION}" \
    -f ./Dockerfile ../..
