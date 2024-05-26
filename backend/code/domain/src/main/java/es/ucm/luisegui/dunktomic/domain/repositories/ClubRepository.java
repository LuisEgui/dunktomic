package es.ucm.luisegui.dunktomic.domain.repositories;

import es.ucm.luisegui.dunktomic.domain.entities.Club;
import es.ucm.luisegui.dunktomic.domain.entities.Court;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ClubRepository
{
    Optional<Club> find(EntityId id);

    Page<Club> find(String name, String district, String postalCode, String streetAddress, Pageable pageable);

    Page<Court> findCourts(EntityId id, Pageable pageable);
}
