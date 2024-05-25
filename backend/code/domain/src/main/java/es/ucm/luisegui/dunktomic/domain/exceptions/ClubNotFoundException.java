package es.ucm.luisegui.dunktomic.domain.exceptions;

import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;

public class ClubNotFoundException extends EntityNotFoundException
{
    public ClubNotFoundException(EntityId clubId) {
        super("CLUB_NOT_FOUND", "Club " + clubId.getId().toString() + " not found", ClubNotFoundException.class, clubId);
    }
}
