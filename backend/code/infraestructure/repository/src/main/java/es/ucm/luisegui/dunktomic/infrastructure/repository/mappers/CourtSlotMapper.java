package es.ucm.luisegui.dunktomic.infrastructure.repository.mappers;

import es.ucm.luisegui.dunktomic.domain.entities.Court;
import es.ucm.luisegui.dunktomic.domain.entities.CourtSlot;
import es.ucm.luisegui.dunktomic.domain.entities.Slot;
import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtState;
import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtType;
import es.ucm.luisegui.dunktomic.domain.valueobjects.Weekday;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.CourtSlotEntity;
import lombok.Data;

@Data
public class CourtSlotMapper
{
    public CourtSlot toEntity(CourtSlotEntity courtSlotEntity)
    {
        CourtSlot courtSlot = new CourtSlot();
        Court court = new Court();
        court.setName(courtSlotEntity.getName());
        court.setType(CourtType.fromValue(courtSlotEntity.getType()));
        courtSlot.setCourt(court);
        Slot slot = new Slot();
        slot.setWeekday(Weekday.fromValue(courtSlotEntity.getWeekday()));
        slot.setStartTime(courtSlotEntity.getStartTime());
        slot.setEndTime(courtSlotEntity.getEndTime());
        courtSlot.setSlot(slot);
        courtSlot.setState(CourtState.fromValue(courtSlotEntity.getState()));

        return courtSlot;
    }
}
