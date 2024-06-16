# Utilizar la imagen de Keycloak como base
FROM quay.io/keycloak/keycloak:latest

# Establecer variables de entorno para el administrador
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=change_me

# Copiar el script de configuración
COPY keycloak/configure-keycloak.sh /opt/keycloak/configure-keycloak.sh

# Dar permisos de ejecución al script y añadir instalación de curl y bash
USER root
RUN chmod +x /opt/keycloak/configure-keycloak.sh

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar el script de configuración al iniciar el contenedor
ENTRYPOINT ["/opt/keycloak/configure-keycloak.sh"]
