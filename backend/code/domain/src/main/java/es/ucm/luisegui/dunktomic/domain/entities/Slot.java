package es.ucm.luisegui.dunktomic.domain.entities;

import es.ucm.luisegui.dunktomic.domain.valueobjects.Weekday;
import lombok.Data;
import java.time.LocalTime;

@Data
public class Slot
{
    private Weekday weekday;
    private LocalTime startTime;
    private LocalTime endTime;
}
