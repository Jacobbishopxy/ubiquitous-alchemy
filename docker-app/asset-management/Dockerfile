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
COPY    ./ubiquitous-asset-management/pom.xml .

# cache dependencies
RUN     mvn dependency:go-offline

# copy source code & resources
COPY    ./ubiquitous-asset-management/src/main/java /app/src/main/java
COPY    ./resources/asset-management /app/src/main/resources

# build the jar
RUN     mvn package -Dmaven.test.skip


# ==============================================================================
# Step: Package image
# ==============================================================================
FROM    ${JAVA_BASE_NAME}:${JAVA_BASE_VERSION}

# copy jar file to `/app`
COPY    --from=builder /app/target/*.jar app.jar
