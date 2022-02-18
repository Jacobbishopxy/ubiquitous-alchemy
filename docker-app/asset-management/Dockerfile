# Author: Jacob Bishop

ARG     JAVA_BASE_NAME
ARG     JAVA_BASE_VERSION

FROM    $JAVA_BASE_NAME:$JAVA_BASE_VERSION

# copy source code
COPY    ./ubiquitous-asset-management /app

# set working directory
WORKDIR /app

# build the jar
RUN     ./mvnw package -Dmaven.test.skip

# copy jar file to `/app`
RUN     cp target/*.jar app.jar
