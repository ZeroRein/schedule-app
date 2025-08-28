FROM php:8.3-apache
ENV TZ=Asia/Tokyo
RUN docker-php-ext-install pdo pdo_mysql
RUN a2enmod headers rewrite
