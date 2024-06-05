package es.ucm.luisegui.dunktomic.application.usecases;

import es.ucm.luisegui.dunktomic.domain.entities.Player;
import es.ucm.luisegui.dunktomic.domain.exceptions.PlayerNotFoundException;
import es.ucm.luisegui.dunktomic.domain.repositories.PlayerRepository;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class FindPlayerUseCase implements UseCase<String, Player>
{
    private final PlayerRepository playerRepository;

    public FindPlayerUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player execute(String input) {
        EntityId id = new EntityId(UUID.fromString(input));
        Optional<Player> foundPlayer = playerRepository.find(id);

        if (foundPlayer.isPresent())
            return foundPlayer.get();
        else
            throw new PlayerNotFoundException(id);
    }
}
