package es.ucm.luisegui.dunktomic.domain.entities;

import es.ucm.luisegui.dunktomic.domain.valueobjects.CourtPositions;
import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
public class Player
{
    private EntityId id;
    @Email
    private String email;
    private String name;
    private Image image;
    @Valid
    @Size(min = 0, max = 10)
    private Float level;
    private Set<CourtPositions> courtPositions;
}
