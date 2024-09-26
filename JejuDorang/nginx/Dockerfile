# nginx/Dockerfile

FROM nginx:latest

COPY ./conf.d /etc/nginx/conf.d
COPY ./entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]
