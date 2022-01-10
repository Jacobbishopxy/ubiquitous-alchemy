#!/usr/bin/env bash


source ../../resources/rust.env

docker build \
    -t "${BASE_IMAGE_NAME}":"${BASE_IMAGE_VERSION}" \
    -f ./Dockerfile .
