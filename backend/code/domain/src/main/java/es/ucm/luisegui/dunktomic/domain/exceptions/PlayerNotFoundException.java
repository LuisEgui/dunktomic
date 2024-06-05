package es.ucm.luisegui.dunktomic.domain.exceptions;

import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;

public class PlayerNotFoundException extends EntityNotFoundException
{
    public PlayerNotFoundException(EntityId playerId) {
        super("PLAYER_NOT_FOUND", "Player " + playerId.getId().toString() + " not found", PlayerNotFoundException.class, playerId);
    }
}
