package es.ucm.luisegui.dunktomic.dtos;

import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtPositions;
import es.ucm.luisegui.dunktomic.rest.dtos.Player;
import java.util.stream.Collectors;

public class PlayerDto
{
    public static Player toModel(es.ucm.luisegui.dunktomic.domain.entities.Player player) {
        return player != null ?
            new Player()
                .id(player.getId().getId())
                .name(player.getName())
                .email(player.getEmail())
                .positionsOnCourt(player.getCourtPositions().stream()
                    .map(pos ->
                        Player.PositionsOnCourtEnum.valueOf(CourtPositions.fromValueAsString(pos.getValue())))
                    .collect(Collectors.toList()))
                .image(new ImageDto().toModel(player.getImage()))
                .level(player.getLevel()) :
            null;
    }
}
