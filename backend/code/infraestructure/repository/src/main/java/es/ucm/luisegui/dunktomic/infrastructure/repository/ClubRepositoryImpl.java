package es.ucm.luisegui.dunktomic.infrastructure.repository;

import es.ucm.luisegui.dunktomic.domain.entities.Club;
import es.ucm.luisegui.dunktomic.domain.repositories.ClubRepository;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import es.ucm.luisegui.dunktomic.infrastructure.database.adapters.ClubDbAdapter;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.ClubEntity;
import es.ucm.luisegui.dunktomic.infrastructure.repository.mappers.ClubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClubRepositoryImpl implements ClubRepository
{
    private final ClubDbAdapter clubDbAdapter;

    @Autowired
    public ClubRepositoryImpl(ClubDbAdapter clubDbAdapter) {
        this.clubDbAdapter = clubDbAdapter;
    }

    @Override
    public Optional<Club> find(EntityId id) {
        ClubMapper clubMapper = new ClubMapper();
        Optional<ClubEntity> clubEntity = clubDbAdapter.find(id.getId());
        return clubEntity.map(clubMapper::toEntity);
    }

    @Override
    public Page<Club> find(String name, String district, String postalCode, String streetAddress, Pageable pageable) {
        Page<ClubEntity> clubsPage = clubDbAdapter.find(name, district, postalCode, streetAddress, pageable);
        return new PageImpl<>(clubsPage.getContent().stream().map(new ClubMapper()::toEntity).collect(Collectors.toList()),
            pageable,
            clubsPage.getTotalElements());
    }

}
