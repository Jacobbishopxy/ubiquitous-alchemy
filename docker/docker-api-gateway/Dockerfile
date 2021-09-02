# Author: Jacob Bishop

ARG     BASE_IMAGE_NAME
ARG     BASE_IMAGE_VERSION

FROM    $BASE_IMAGE_NAME:$BASE_IMAGE_VERSION

WORKDIR /app

COPY    ./ubiquitous-api-gateway /app

RUN     go mod tidy
RUN     go build

ENV     GIN_MODE=release
