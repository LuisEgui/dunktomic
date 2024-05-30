package es.ucm.luisegui.dunktomic.application.usecases;

import es.ucm.luisegui.dunktomic.application.dtos.CreatePlayerDto;
import es.ucm.luisegui.dunktomic.domain.entities.Player;
import es.ucm.luisegui.dunktomic.domain.exceptions.PlayerAlreadyExistsException;
import es.ucm.luisegui.dunktomic.domain.repositories.PlayerRepository;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CreatePlayerUseCase implements UseCase<CreatePlayerDto, String>
{
    private final PlayerRepository playerRepository;

    public CreatePlayerUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public String execute(CreatePlayerDto input) {
        EntityId playerId = input.getPlayer().getId();
        Optional<Player> player = playerRepository.find(playerId);

        if (player.isPresent()) {
            throw new PlayerAlreadyExistsException(playerId);
        } else {
            return String.valueOf(playerRepository.save(input.getPlayer(), input.getPassword()).getId());
        }
    }
}
