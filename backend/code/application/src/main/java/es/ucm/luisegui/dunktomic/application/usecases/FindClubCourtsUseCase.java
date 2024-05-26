package es.ucm.luisegui.dunktomic.application.usecases;

import es.ucm.luisegui.dunktomic.application.dtos.FindClubCourtsDto;
import es.ucm.luisegui.dunktomic.domain.entities.Court;
import es.ucm.luisegui.dunktomic.domain.repositories.ClubRepository;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class FindClubCourtsUseCase implements UseCase<FindClubCourtsDto, Page<Court>>
{
    private final ClubRepository clubRepository;

    public FindClubCourtsUseCase(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public Page<Court> execute(FindClubCourtsDto input) {
        EntityId id = new EntityId(UUID.fromString(input.getId()));
        return clubRepository.findCourts(id, input.getPageable());
    }
}
