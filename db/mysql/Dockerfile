# Author: Luis Egui

FROM mysql:8.4.0

# Initialization scripts
COPY mysql_settings.cnf /etc/mysql/conf.d/
COPY init.sql /docker-entrypoint-initdb.d/
#COPY tables_creation.sql /docker-entrypoint-initdb.d/

ENV MYSQL_RANDOM_ROOT_PASSWORD=true

# Expose port
EXPOSE 3306
