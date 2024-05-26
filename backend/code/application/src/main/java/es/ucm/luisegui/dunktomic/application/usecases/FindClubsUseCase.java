package es.ucm.luisegui.dunktomic.application.usecases;

import es.ucm.luisegui.dunktomic.application.dtos.FindClubsFilters;
import es.ucm.luisegui.dunktomic.domain.entities.Club;
import es.ucm.luisegui.dunktomic.domain.repositories.ClubRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FindClubsUseCase implements UseCase<FindClubsFilters, Page<Club>>
{
    private final ClubRepository clubRepository;

    public FindClubsUseCase(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public Page<Club> execute(FindClubsFilters input) {
        return clubRepository.find(input.getName(), input.getDistrict(), input.getPostalCode(), input.getStreetAddress(), input.getPageable());
    }
}
