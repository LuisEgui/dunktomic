package es.ucm.luisegui.dunktomic.controllers;

import es.ucm.luisegui.dunktomic.application.usecases.FindClubUseCase;
import es.ucm.luisegui.dunktomic.dtos.ClubDto;
import es.ucm.luisegui.dunktomic.rest.ClubsApi;
import es.ucm.luisegui.dunktomic.rest.dtos.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClubsController implements ClubsApi
{
    private final FindClubUseCase findClubUseCase;

    @Autowired
    public ClubsController(FindClubUseCase findClubUseCase) {
        this.findClubUseCase = findClubUseCase;
    }

    @Override
    public ResponseEntity<Club> getClub(String id, String acceptLanguage) {
        es.ucm.luisegui.dunktomic.domain.entities.Club club = findClubUseCase.execute(id);

        return ResponseEntity.ok().body(ClubDto.toModel(club));
    }
}
