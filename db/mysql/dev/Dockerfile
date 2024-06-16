# Author: Luis Egui

FROM mysql:8.4.0

# Initialization scripts
COPY db/mysql/dev/mysql_settings.cnf /etc/mysql/conf.d/
COPY db/mysql/dev/init.sql /docker-entrypoint-initdb.d/
COPY db/mysql/dev/tables_creation.sql /docker-entrypoint-initdb.d/
COPY db/mysql/dev/triggers_creation.sql /docker-entrypoint-initdb.d/
COPY db/mysql/dev/x_dump_data.sql /docker-entrypoint-initdb.d/

#ENV MYSQL_RANDOM_ROOT_PASSWORD=true
