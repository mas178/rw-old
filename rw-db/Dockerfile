FROM mariadb:10.1
MAINTAINER Masaaki Inaba
COPY ./mariadb.cnf /etc/mysql/conf.d/mariadb.cnf
COPY ./ddl.sql /docker-entrypoint-initdb.d/
