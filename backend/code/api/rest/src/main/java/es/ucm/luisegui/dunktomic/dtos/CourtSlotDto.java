package es.ucm.luisegui.dunktomic.dtos;

import es.ucm.luisegui.dunktomic.rest.dtos.CourtSlot;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CourtSlotDto
{
    public static CourtSlot toModel(es.ucm.luisegui.dunktomic.domain.entities.CourtSlot courtSlot) {
        return courtSlot != null ?
            new CourtSlot()
                .name(courtSlot.getCourt().getName())
                .type(CourtSlot.TypeEnum.fromValue(courtSlot.getCourt().getType().getValue()))
                .weekday(CourtSlot.WeekdayEnum.fromValue(courtSlot.getSlot().getWeekday().getValue()))
                .startTime(formatLocalTime(courtSlot.getSlot().getStartTime()))
                .endTime(formatLocalTime(courtSlot.getSlot().getEndTime()))
                .state(CourtSlot.StateEnum.fromValue(courtSlot.getState().getValue())) :
            null;
    }

    private static String formatLocalTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
