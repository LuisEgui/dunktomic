package es.ucm.luisegui.dunktomic.domain.entities;

import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtType;
import lombok.Data;

@Data
public class Court
{
    private String name;
    private CourtType type;
}
