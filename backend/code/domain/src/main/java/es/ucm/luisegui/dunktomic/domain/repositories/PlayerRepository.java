package es.ucm.luisegui.dunktomic.domain.repositories;

import es.ucm.luisegui.dunktomic.domain.entities.Player;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import java.util.Optional;

public interface PlayerRepository
{
    Optional<Player> find(EntityId id);

    EntityId save(Player player, String password);

}
