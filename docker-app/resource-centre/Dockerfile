# Author: Jacob Bishop
#
# Caching Maven dependencies:
# https://nieldw.medium.com/caching-maven-dependencies-in-a-docker-build-dca6ca7ad612

ARG     MAVEN_IMAGE_NAME
ARG     MAVEN_IMAGE_VERSION
ARG     JAVA_BASE_NAME
ARG     JAVA_BASE_VERSION

# ==============================================================================
# Step: Test and package
# ==============================================================================
FROM    ${MAVEN_IMAGE_NAME}:${MAVEN_IMAGE_VERSION} AS builder

# set working directory
WORKDIR /app

# copy pom.xml
COPY    ./ubiquitous-resources-centre/pom.xml .

# cache dependencies
RUN     mvn dependency:go-offline

# copy source code & resources
COPY    ./ubiquitous-resources-centre/src/main/java /app/src/main/java
COPY    ./resources/resources-centre /app/src/main/resources

# build the jar
RUN     mvn package && cp target/*.jar app.jar


# ==============================================================================
# Step: Package image
# ==============================================================================
FROM    ${JAVA_BASE_NAME}:${JAVA_BASE_VERSION}

WORKDIR /app

# copy jar file from builder image
COPY    --from=builder /app/app.jar ./app.jar
