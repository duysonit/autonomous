FROM nginx:alpine

WORKDIR /usr/share/nginx/html

COPY target/site/serenity .

EXPOSE 80
