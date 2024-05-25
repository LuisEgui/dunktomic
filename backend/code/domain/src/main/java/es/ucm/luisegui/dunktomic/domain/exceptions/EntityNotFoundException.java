package es.ucm.luisegui.dunktomic.domain.exceptions;

import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends DomainException
{
    private final Class<?> entityClass;
    private final EntityId entityId;

    public EntityNotFoundException(String code, String description, Class<?> entityClass, EntityId entityId) {
        super(code, description);
        this.entityClass = entityClass;
        this.entityId = entityId;
    }
}
