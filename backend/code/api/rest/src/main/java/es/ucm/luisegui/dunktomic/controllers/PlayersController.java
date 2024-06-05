package es.ucm.luisegui.dunktomic.controllers;

import es.ucm.luisegui.dunktomic.application.usecases.CreatePlayerUseCase;
import es.ucm.luisegui.dunktomic.application.usecases.FindPlayerUseCase;
import es.ucm.luisegui.dunktomic.dtos.CreatePlayerRequestDto;
import es.ucm.luisegui.dunktomic.rest.PlayersApi;
import es.ucm.luisegui.dunktomic.rest.dtos.Player;
import es.ucm.luisegui.dunktomic.rest.dtos.PostPlayerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayersController implements PlayersApi
{
    private final CreatePlayerUseCase createPlayerUseCase;
    private final FindPlayerUseCase findPlayerUseCase;

    public PlayersController(CreatePlayerUseCase createPlayerUseCase, FindPlayerUseCase findPlayerUseCase) {
        this.createPlayerUseCase = createPlayerUseCase;
        this.findPlayerUseCase = findPlayerUseCase;
    }

    @Override
    public ResponseEntity<Void> postPlayer(String acceptLanguage, PostPlayerRequest postPlayerRequest) {
        String id = createPlayerUseCase.execute(CreatePlayerRequestDto.toModel(postPlayerRequest));
        return ResponseEntity.accepted().header("x-resource-id", id).build();
    }

    @Override
    public ResponseEntity<Player> getPlayer(String id) {
        es.ucm.luisegui.dunktomic.domain.entities.Player player = findPlayerUseCase.execute(id);
        return ResponseEntity.ok().body(es.ucm.luisegui.dunktomic.dtos.PlayerDto.toModel(player));
    }
}
