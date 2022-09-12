# docker build -t leeshop-mysql-db -f leeshop-mysql-db.dockerfile .
# docker exec -it leeshop-mysql-db bash
# docker run --name leeshop-mysql-db -it -p 3308:3306 leeshop-mysql-db

FROM mysql:8.0.30

COPY ./src/db/leeshop.sql /docker-entrypoint-initdb.d/

ENV MYSQL_DATABASE=leeshop
ENV MYSQL_ROOT_PASSWORD=root