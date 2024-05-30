package es.ucm.luisegui.dunktomic.domain.entities;

import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class User
{
    private EntityId id;
    @Email
    private String email;
    private String name;
}
