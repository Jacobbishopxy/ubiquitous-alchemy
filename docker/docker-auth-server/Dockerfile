####################################################################################################
## Builder
####################################################################################################

# temperary solution; need refactor to cross-compile to lower the image size
ARG     BASE_IMAGE_NAME
ARG     BASE_IMAGE_VERSION

FROM $BASE_IMAGE_NAME:$BASE_IMAGE_VERSION as builder

RUN apt-get install pkg-config libssl-dev openssl -y

WORKDIR /docker

COPY ./ubiquitous-auth-server .

RUN cargo build --release

####################################################################################################
## Final image
####################################################################################################
# FROM debian:buster-slim

# # Import from builder.
# COPY --from=builder /etc/passwd /etc/passwd
# COPY --from=builder /etc/group /etc/group

# WORKDIR /docker

# # Copy our build
# COPY --from=builder /docker/target/release/ubiquitous-auth-server ./

# Use an unprivileged user.
# USER docker:docker

# CMD ["./ubiquitous-auth-server"]