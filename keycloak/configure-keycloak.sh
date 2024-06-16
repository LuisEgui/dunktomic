#!/bin/bash

/opt/keycloak/bin/kc.sh start-dev &
KEYCLOAK_PID=$!

# Función para esperar a que el puerto esté disponible
wait_for_port() {
  local host=$1
  local port=$2
  while ! (echo > /dev/tcp/$host/$port) &> /dev/null; do
    echo "Esperando a que Keycloak inicie en $host:$port..."
    sleep 5
  done
}

# Esperar a que Keycloak inicie en el puerto 8080
wait_for_port localhost 8080

/opt/keycloak/bin/kcadm.sh help config

# Autenticar como administrador
/opt/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080 --realm master --user $KEYCLOAK_ADMIN --password $KEYCLOAK_ADMIN_PASSWORD

# Configuración del cliente en formato JSON
CLIENT_CONFIG='{
  "clientId": "admin-cli",
  "name": "${client_admin-cli}",
  "description": "",
  "rootUrl": "",
  "adminUrl": "",
  "baseUrl": "",
  "surrogateAuthRequired": false,
  "enabled": true,
  "alwaysDisplayInConsole": false,
  "clientAuthenticatorType": "client-secret",
  "secret": "DEBlxI6aaypLnAUPw7gI7wFDEiLq8dsS",
  "redirectUris": [],
  "webOrigins": [],
  "notBefore": 0,
  "bearerOnly": false,
  "consentRequired": false,
  "standardFlowEnabled": true,
  "implicitFlowEnabled": false,
  "directAccessGrantsEnabled": true,
  "serviceAccountsEnabled": true,
  "publicClient": false,
  "frontchannelLogout": false,
  "protocol": "openid-connect",
  "attributes": {
    "oidc.ciba.grant.enabled": "false",
    "client.secret.creation.time": "1717010285",
    "backchannel.logout.session.required": "true",
    "oauth2.device.authorization.grant.enabled": "false",
    "display.on.consent.screen": "false",
    "backchannel.logout.revoke.offline.tokens": "false"
  },
  "authenticationFlowBindingOverrides": {},
  "fullScopeAllowed": false,
  "nodeReRegistrationTimeout": 0,
  "protocolMappers": [
    {
      "name": "Client Host",
      "protocol": "openid-connect",
      "protocolMapper": "oidc-usersessionmodel-note-mapper",
      "consentRequired": false,
      "config": {
        "user.session.note": "clientHost",
        "introspection.token.claim": "true",
        "id.token.claim": "true",
        "access.token.claim": "true",
        "claim.name": "clientHost",
        "jsonType.label": "String"
      }
    },
    {
      "name": "Client IP Address",
      "protocol": "openid-connect",
      "protocolMapper": "oidc-usersessionmodel-note-mapper",
      "consentRequired": false,
      "config": {
        "user.session.note": "clientAddress",
        "introspection.token.claim": "true",
        "id.token.claim": "true",
        "access.token.claim": "true",
        "claim.name": "clientAddress",
        "jsonType.label": "String"
      }
    },
    {
      "name": "Client ID",
      "protocol": "openid-connect",
      "protocolMapper": "oidc-usersessionmodel-note-mapper",
      "consentRequired": false,
      "config": {
        "user.session.note": "client_id",
        "introspection.token.claim": "true",
        "id.token.claim": "true",
        "access.token.claim": "true",
        "claim.name": "client_id",
        "jsonType.label": "String"
      }
    }
  ],
  "defaultClientScopes": [
    "web-origins",
    "acr",
    "profile",
    "roles",
    "email"
  ],
  "optionalClientScopes": [
    "address",
    "phone",
    "offline_access",
    "microprofile-jwt"
  ],
  "access": {
    "view": true,
    "configure": true,
    "manage": true
  }
}'

# Convertir la configuración del cliente en un archivo JSON temporal
echo "$CLIENT_CONFIG" > /tmp/client-config.json

# Obtener el ID del cliente "admin-cli"
CLIENT_ID=$(/opt/keycloak/bin/kcadm.sh get clients -r $REALM | grep -A 10 -B 10 '"clientId" : "admin-cli"' | grep '"id" :' | sed -E 's/.*"id" : "([^"]+)".*/\1/')

# Actualizar el cliente con la configuración especificada
/opt/keycloak/bin/kcadm.sh update clients/$CLIENT_ID -r $REALM -f /tmp/client-config.json

# Logout (eliminar la configuración de credenciales)
#/opt/keycloak/bin/kcadm.sh config delete
#rm ~/.keycloak/kcadm.config

# Mantener el contenedor en ejecución
tail -f /dev/null
