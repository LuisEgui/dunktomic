package es.ucm.luisegui.dunktomic.infrastructure.repository.mappers;

import es.ucm.luisegui.dunktomic.domain.entities.Court;
import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtType;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.CourtEntity;
import lombok.Data;

@Data
public class CourtMapper
{
    public Court toEntity(CourtEntity courtEntity)
    {
        Court court = new Court();
        court.setName(courtEntity.getName());
        court.setType(CourtType.fromValue(courtEntity.getType()));
        return court;
    }
}
