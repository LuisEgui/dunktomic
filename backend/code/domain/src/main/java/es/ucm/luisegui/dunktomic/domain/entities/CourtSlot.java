package es.ucm.luisegui.dunktomic.domain.entities;

import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtState;
import lombok.Data;

@Data
public class CourtSlot
{
    private Court court;
    private Slot slot;
    private CourtState state;
}
