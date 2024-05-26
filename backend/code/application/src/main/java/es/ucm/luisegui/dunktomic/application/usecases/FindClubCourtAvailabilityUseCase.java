package es.ucm.luisegui.dunktomic.application.usecases;

import es.ucm.luisegui.dunktomic.application.dtos.FindClubCourtAvailabilityDto;
import es.ucm.luisegui.dunktomic.domain.entities.CourtSlot;
import es.ucm.luisegui.dunktomic.domain.repositories.ClubRepository;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class FindClubCourtAvailabilityUseCase implements UseCase<FindClubCourtAvailabilityDto, Page<CourtSlot>>
{
    private final ClubRepository clubRepository;

    public FindClubCourtAvailabilityUseCase(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public Page<CourtSlot> execute(FindClubCourtAvailabilityDto input) {
        EntityId id = new EntityId(UUID.fromString(input.getId()));
        return clubRepository.findCourtSlots(id, input.getCourtName(), input.getCourtType(), input.getWeekDay());
    }
}
