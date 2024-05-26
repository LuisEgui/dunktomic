package es.ucm.luisegui.dunktomic.dtos;

import es.ucm.luisegui.dunktomic.rest.dtos.Court;

public class CourtDto
{
    public static Court toModel(es.ucm.luisegui.dunktomic.domain.entities.Court court) {
        return court != null ?
            new Court()
                .name(court.getName())
                .type(Court.TypeEnum.fromValue(court.getType().getValue())) :
            null;
    }
}
