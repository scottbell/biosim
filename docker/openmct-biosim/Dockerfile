# Patched build for https://github.com/scottbell/openmct-biosim
#
# Why this exists: the upstream Dockerfile builds directly from a remote git
# context and pins nothing, so it silently breaks whenever nasa/openmct's
# master branch moves. Two upstream regressions currently make the original
# Dockerfile fail:
#
#   1. nasa/openmct's package.json now requires Node >= 24, but the upstream
#      Dockerfile uses node:20-alpine.
#   2. When openmct-biosim installs openmct with
#      `npm install nasa/openmct#master --no-save`, npm packs the git repo
#      before installing it and strips dotfiles/dot-directories (including
#      openmct's `.webpack/` config). That makes `npm run build:prod` inside
#      node_modules/openmct fail (missing webpack config), so
#      node_modules/openmct/dist is never produced — which is exactly the
#      "not found" error `docker compose up` reports.
#
# The fix: bump to node:24-alpine, run openmct-biosim's normal build first,
# then replace node_modules/openmct with a direct `git clone` (which keeps
# dotfiles) and build openmct itself from that clone.

FROM node:24-alpine AS builder
RUN apk add --no-cache git
RUN git clone https://github.com/scottbell/openmct-biosim /app/openmct-biosim
WORKDIR /app/openmct-biosim
RUN npm install && npm run build:prod && \
    rm -rf node_modules/openmct && \
    git clone --depth 1 https://github.com/nasa/openmct node_modules/openmct && \
    cd node_modules/openmct && npm install && npm run build:prod

FROM nginx:alpine
ENV NGINX_ENTRYPOINT_QUIET_LOGS=1
RUN sed -i 's/access_log .*/access_log off;/' /etc/nginx/nginx.conf && \
    sed -i 's/error_log .*/error_log \/var\/log\/nginx\/error.log crit;/' /etc/nginx/nginx.conf

RUN mkdir -p /usr/share/nginx/html/openmct
COPY --from=builder /app/openmct-biosim/node_modules/openmct/dist/ /usr/share/nginx/html/openmct/
COPY --from=builder /app/openmct-biosim/dist/openmct-biosim.js.map /usr/share/nginx/html
COPY --from=builder /app/openmct-biosim/dist/openmct-biosim.js /usr/share/nginx/html
COPY --from=builder /app/openmct-biosim/etc/prod/index.js /usr/share/nginx/html/
COPY --from=builder /app/openmct-biosim/etc/prod/index.html /usr/share/nginx/html/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]