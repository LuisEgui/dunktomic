package es.ucm.luisegui.dunktomic.domain.exceptions;

import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;

public class PlayerAlreadyExistsException extends DomainException
{
    public PlayerAlreadyExistsException(EntityId playerId) {
        super("PLAYER_ALREADY_EXISTS", "Player " + playerId.getId().toString() + " already exists");
    }
}
