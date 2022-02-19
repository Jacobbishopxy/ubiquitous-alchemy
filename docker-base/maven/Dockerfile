# Author: Jacob Bishop

ARG     MAVEN_IMAGE_NAME
ARG     MAVEN_IMAGE_VERSION

FROM    $MAVEN_IMAGE_NAME:$MAVEN_IMAGE_VERSION

# set timezone
ENV     TZ=Asia/Shanghai
RUN     ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

