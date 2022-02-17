# Author: Jacob Bishop

ARG     JAVA_IMAGE_NAME
ARG     JAVA_IMAGE_VERSION

FROM    $JAVA_IMAGE_NAME:$JAVA_IMAGE_VERSION

# set timezone
ENV     TZ=Asia/Shanghai
RUN     ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

