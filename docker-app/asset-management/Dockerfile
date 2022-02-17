# Author: Jacob Bishop

ARG     AM_BASE_NAME
ARG     AM_BASE_VERSION
ARG     JAR_FILE

FROM    $AM_BASE_NAME:$AM_BASE_VERSION

# copy source code
COPY    ./ubiquitous-asset-management /app

# set working directory
WORKDIR /app

# build the jar
RUN     ./mvnw package

# copy the jar to the container
COPY    ${JAR_FILE} app.jar
