package es.ucm.luisegui.dunktomic.infrastructure.repository.mappers;

import es.ucm.luisegui.dunktomic.domain.entities.Club;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.ClubEntity;
import lombok.Data;

@Data
public class ClubMapper
{
    public Club toEntity(ClubEntity clubEntity)
    {
        Club club = new Club();
        club.setId(new EntityId(clubEntity.getId()));
        club.setName(clubEntity.getName());
        club.setDistrict(clubEntity.getDistrict());
        club.setStreetAddress(clubEntity.getStreetAddress());
        club.setPostalCode(clubEntity.getPostalCode());
        club.setLatitude(clubEntity.getLatitude());
        club.setLongitude(clubEntity.getLongitude());
        club.setImage(new ImageMapper().toEntity(clubEntity.getImage()));
        return club;
    }
}
