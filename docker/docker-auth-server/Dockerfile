####################################################################################################
## Builder
####################################################################################################

ARG     BASE_IMAGE_NAME
ARG     BASE_IMAGE_VERSION

FROM    $BASE_IMAGE_NAME:$BASE_IMAGE_VERSION as builder

# Dependencies
RUN     apt-get -y update && \
        apt-get -y install libssl-dev pkg-config openssl && \
        ln -s libssl.so.1.0.0 libssl.so.10 && \
        ln -s libcrypto.so.1.0.0 libcrypto.so.10

# Project dependencies
RUN     apt-get install -y libpq-dev

WORKDIR /app

COPY    ./ubiquitous-auth-server .

# Build application
RUN     cargo build --release

####################################################################################################
## Final image
####################################################################################################
FROM    debian:buster-slim

# Import from builder.
COPY    --from=builder /etc/passwd /etc/passwd
COPY    --from=builder /etc/group /etc/group
COPY    --from=builder /usr/lib/x86_64-linux-gnu /usr/lib/x86_64-linux-gnu

WORKDIR /app

# Copy our build
COPY    --from=builder /app/target/release/ubiquitous-auth-server ./
COPY    --from=builder /app/.env ./

# Use an unprivileged user.
USER    docker:docker
