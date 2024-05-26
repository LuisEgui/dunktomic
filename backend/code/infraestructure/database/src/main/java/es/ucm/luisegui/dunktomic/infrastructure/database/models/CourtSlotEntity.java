package es.ucm.luisegui.dunktomic.infrastructure.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtSlotEntity
{
    private String name;
    private String type;
    private String weekday;
    private LocalTime startTime;
    private LocalTime endTime;
    private String state;

    public static class Field
    {
        public static final String NAME = "name";
        public static final String TYPE = "type";
        public static final String WEEKDAY = "weekday";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";
        public static final String STATE = "state";
    }
}
