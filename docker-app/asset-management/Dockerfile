# Author: Jacob Bishop

ARG     AM_BASE_NAME
ARG     AM_BASE_VERSION
ARG     JAR_FILE

FROM    $AM_BASE_NAME:$AM_BASE_VERSION

# TODO: build the jar file outside of the container
COPY    ./ubiquitous-asset-management /app

# set working directory
WORKDIR /app

# build the jar
RUN     ./mvnw clean package

