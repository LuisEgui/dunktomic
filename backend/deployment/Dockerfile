# Etapa de construcción
FROM maven:3-openjdk-17 AS build

# Configura el directorio de trabajo
WORKDIR /dunktomic

# Copia el archivo de configuración de Maven y el código fuente
COPY backend/code/ ./

# Instala el POM padre en el repositorio local
RUN mvn clean package

# Ejecuta la aplicación utilizando Maven Spring Boot
CMD ["mvn", "spring-boot:run"]

EXPOSE 8081
