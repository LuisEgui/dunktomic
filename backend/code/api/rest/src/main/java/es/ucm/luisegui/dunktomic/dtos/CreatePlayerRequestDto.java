package es.ucm.luisegui.dunktomic.dtos;

import es.ucm.luisegui.dunktomic.application.dtos.CreatePlayerDto;
import es.ucm.luisegui.dunktomic.domain.entities.Player;
import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtPositions;
import lombok.Data;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CreatePlayerRequestDto
{
    public static CreatePlayerDto toModel(es.ucm.luisegui.dunktomic.rest.dtos.PostPlayerRequest postPlayerRequest) {
        if (postPlayerRequest == null)
            return null;

        CreatePlayerDto createPlayerDto = new CreatePlayerDto();
        Player player = new Player();
        player.setEmail(postPlayerRequest.getEmail());
        player.setName(postPlayerRequest.getName());
        Set<CourtPositions> courtPositions = postPlayerRequest.getPositionsOnCourt().stream()
                .map(position -> CourtPositions.valueOf(position.getValue()))
                .collect(Collectors.toSet());
        player.setCourtPositions(courtPositions);
        createPlayerDto.setPlayer(player);
        createPlayerDto.setPassword(postPlayerRequest.getPassword());
        return createPlayerDto;
    }
}
