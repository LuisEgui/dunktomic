package es.ucm.luisegui.dunktomic.application.usecases;

import es.ucm.luisegui.dunktomic.domain.entities.Club;
import es.ucm.luisegui.dunktomic.domain.exceptions.ClubNotFoundException;
import es.ucm.luisegui.dunktomic.domain.repositories.ClubRepository;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class FindClubUseCase implements UseCase<String, Club>
{
    private final ClubRepository clubRepository;

    public FindClubUseCase(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public Club execute(String input) {
        EntityId id = new EntityId(UUID.fromString(input));
        Optional<Club> foundClub = clubRepository.find(id);

        if (foundClub.isPresent())
            return foundClub.get();
        else
            throw new ClubNotFoundException(id);
    }

}
