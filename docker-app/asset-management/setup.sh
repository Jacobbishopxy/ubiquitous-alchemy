#!/usr/bin/env bash
# author: Jacob Bishop

source ../../resources/java.env
docker build \
		-t "${AM_BASE_NAME}":"${AM_BASE_VERSION}" \
    --build-arg AM_BASE_NAME="${AM_BASE_NAME}" \
    --build-arg AM_BASE_VERSION="${AM_BASE_VERSION}" \
    -f ./Dockerfile ../..
