package es.ucm.luisegui.dunktomic.infrastructure.repository.dto;

import es.ucm.luisegui.dunktomic.infrastructure.keycloakclient.model.CredentialRepresentation;
import es.ucm.luisegui.dunktomic.infrastructure.keycloakclient.model.UserRepresentation;
import es.ucm.luisegui.dunktomic.domain.entities.Player;
import java.util.List;

public class PlayerAccount
{
    public static UserRepresentation toUserRepresentation(Player player, String password) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(player.getEmail());
        userRepresentation.setUsername(player.getName());
        userRepresentation.setEnabled(true);
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType("password");
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        return userRepresentation;
    }
}
