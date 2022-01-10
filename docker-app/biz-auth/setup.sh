#!/usr/bin/env bash
# author: Celia Xiao

source ../../resources/rust.env
source ../../resources/auth.env
docker build \
    -t "${APP_IMAGE_NAME}":"${APP_IMAGE_VERSION}" \
    --build-arg BASE_IMAGE_NAME="${BASE_IMAGE_NAME}" \
    --build-arg BASE_IMAGE_VERSION="${BASE_IMAGE_VERSION}" \
    -f ./Dockerfile ../..
