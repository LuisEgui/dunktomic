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
      "name": "Client ID",
      "protocol": "openid-connect",
      "protocolMapper": "oidc-usersessionmodel-note-mapper",
      "consentRequired": false,
      "config": {
        "user.session.note": "client_id",
        "id.token.claim": "true",
        "introspection.token.claim": "true",
        "access.token.claim": "true",
        "claim.name": "client_id",
        "jsonType.label": "String"
      }
    },
    {
      "name": "Client Host",
      "protocol": "openid-connect",
      "protocolMapper": "oidc-usersessionmodel-note-mapper",
      "consentRequired": false,
      "config": {
        "user.session.note": "clientHost",
        "id.token.claim": "true",
        "introspection.token.claim": "true",
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
        "id.token.claim": "true",
        "introspection.token.claim": "true",
        "access.token.claim": "true",
        "claim.name": "clientAddress",
        "jsonType.label": "String"
      }
    }
  ],
  "defaultClientScopes": [
    "web-origins",
    "acr",
    "profile",
    "roles",
    "basic",
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
CLIENT_ID=$(/opt/keycloak/bin/kcadm.sh get clients --realm master | grep -A 10 -B 10 '"clientId" : "admin-cli"' | grep '"id"' | head -n 1 | sed -E 's/.*"id" : "([^"]+)".*/\1/')
echo "CLIENT_ID: $CLIENT_ID"

# Actualizar el cliente con la configuración especificada
/opt/keycloak/bin/kcadm.sh update clients/$CLIENT_ID --realm master -f /tmp/client-config.json

# Crear el rol manage-users para el cliente admin-cli
/opt/keycloak/bin/kcadm.sh create clients/$CLIENT_ID/roles --realm master -s name=manage-users -s description="Role to manage users"

# Obtener el ID del rol manage-users del cliente admin-cli
CLIENT_ROLE_SEARCH_RESULT=$(/opt/keycloak/bin/kcadm.sh get clients/$CLIENT_ID/roles --realm master | grep -A 10 -B 10 '"name" : "manage-users"' | grep '"id" :' | head -n 1 | sed -E 's/.*"id" : "([^"]+)".*/\1/')
echo "CLIENT_ROLE_ID: $CLIENT_ROLE_SEARCH_RESULT"

# Obtener el ID del rol manage-users del cliente master-realm utilizando el endpoint correcto
ROLE_SEARCH_RESULT=$(/opt/keycloak/bin/kcadm.sh get ui-ext/available-roles/roles/$CLIENT_ROLE_SEARCH_RESULT -r master -q search=manage-users)
REALM_ROLE_ID=$(echo "$ROLE_SEARCH_RESULT" | grep '"id" :' | sed -E 's/.*"id" : "([^"]+)".*/\1/')
echo "REALM_ROLE_ID: $REALM_ROLE_ID"

# Construir el objeto JSON para la asociación de roles, asegurando que los caracteres se escapen correctamente
COMPOSITE_ROLE_JSON=$(cat <<EOF
[
  {
    "id": "$REALM_ROLE_ID",
    "name": "manage-users",
    "description": "\${role_manage-users}"
  }
]
EOF
)

# Verificar que el JSON es correcto
echo "COMPOSITE_ROLE_JSON: $COMPOSITE_ROLE_JSON"

# Asociar el rol manage-users del realm master al rol manage-users del cliente admin-cli
echo "$COMPOSITE_ROLE_JSON" | /opt/keycloak/bin/kcadm.sh create roles-by-id/$CLIENT_ROLE_SEARCH_RESULT/composites --realm master -f -

# Asociar el rol manage-users del cliente admin-cli al rol manage-users del realm master
echo "$COMPOSITE_ROLE_JSON" | /opt/keycloak/bin/kcadm.sh create roles-by-id/$REALM_ROLE_ID/composites --realm master -f -

# Obtener el ID del service account del cliente admin-cli
SERVICE_ACCOUNT_JSON=$(/opt/keycloak/bin/kcadm.sh get clients/$CLIENT_ID/service-account-user --realm master)
SERVICE_ACCOUNT_ID=$(echo "$SERVICE_ACCOUNT_JSON" | grep '"id"' | head -n 1 | sed -E 's/.*"id" : "([^"]+)".*/\1/')
echo "SERVICE_ACCOUNT_ID: $SERVICE_ACCOUNT_ID"

SEVICE_ACCOUNT_ROLE_JSON=$(cat <<EOF
[
  {
    "id": "$CLIENT_ROLE_SEARCH_RESULT",
    "name": "manage-users",
    "description": "\${role_manage-users}"
  }
]
EOF
)

echo "SEVICE_ACCOUNT_ROLE_JSON: $SEVICE_ACCOUNT_ROLE_JSON"

echo "$SEVICE_ACCOUNT_ROLE_JSON" | /opt/keycloak/bin/kcadm.sh create users/$SERVICE_ACCOUNT_ID/role-mappings/clients/$CLIENT_ID --realm master -f -

# Logout (eliminar la configuración de credenciales)
rm ~/.keycloak/kcadm.config

# Mantener el contenedor en ejecución
tail -f /dev/null
