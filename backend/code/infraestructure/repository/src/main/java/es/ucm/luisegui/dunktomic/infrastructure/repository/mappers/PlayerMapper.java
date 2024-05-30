package es.ucm.luisegui.dunktomic.infrastructure.repository.mappers;

import es.ucm.luisegui.dunktomic.domain.entities.Player;
import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtPositions;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.PlayerEntity;
import lombok.Data;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PlayerMapper
{
    public Player toEntity(PlayerEntity playerEntity)
    {
        Player player = new Player();
        player.setId(new EntityId(playerEntity.getId()));
        player.setEmail(playerEntity.getEmail());
        player.setName(playerEntity.getName());
        player.setImage(new ImageMapper().toEntity(playerEntity.getImage()));
        Set<CourtPositions> courtPositions = playerEntity.getCourtPositions().stream()
                .map(CourtPositions::fromValue)
                .collect(java.util.stream.Collectors.toSet());
        player.setCourtPositions(courtPositions);
        player.setLevel(playerEntity.getLevel());
        return player;
    }

    public PlayerEntity fromEntity(Player player)
    {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(player.getId().getId());
        playerEntity.setEmail(player.getEmail());
        playerEntity.setName(player.getName());
        Set<String> courtPositions = player.getCourtPositions().stream()
                .map(CourtPositions::getValue)
                .collect(Collectors.toSet());
        playerEntity.setCourtPositions(courtPositions);
        playerEntity.setLevel(player.getLevel());
        playerEntity.setImage(new ImageMapper().fromEntity(player.getImage()));
        return playerEntity;
    }
}
