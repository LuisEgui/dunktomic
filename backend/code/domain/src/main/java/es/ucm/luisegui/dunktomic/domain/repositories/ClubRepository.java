package es.ucm.luisegui.dunktomic.domain.repositories;

import es.ucm.luisegui.dunktomic.domain.entities.Club;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import java.util.Optional;

public interface ClubRepository
{
    Optional<Club> find(EntityId id);
}
