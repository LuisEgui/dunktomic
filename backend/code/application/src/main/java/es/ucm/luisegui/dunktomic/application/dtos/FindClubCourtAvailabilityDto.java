package es.ucm.luisegui.dunktomic.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindClubCourtAvailabilityDto
{
    private String id;
    private String courtName;
    private String courtType;
    private String weekDay;
}
