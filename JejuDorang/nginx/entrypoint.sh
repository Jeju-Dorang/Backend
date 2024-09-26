#!/bin/bash

if [ -f /etc/nginx/certs/live/www.jejudorang.site/fullchain.pem ]; then
    echo "SSL certificates found, enabling HTTPS"
else
    echo "SSL certificates not found, running in HTTP mode"
    sed -i '/listen 443 ssl;/d' /etc/nginx/conf.d/default.conf
    sed -i '/ssl_certificate/d' /etc/nginx/conf.d/default.conf
    sed -i '/ssl_certificate_key/d' /etc/nginx/conf.d/default.conf
    sed -i '/ssl_protocols/d' /etc/nginx/conf.d/default.conf
    sed -i '/ssl_ciphers/d' /etc/nginx/conf.d/default.conf
fi

exec "$@"
