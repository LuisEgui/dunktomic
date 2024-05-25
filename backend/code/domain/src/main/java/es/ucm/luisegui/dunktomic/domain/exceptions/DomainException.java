package es.ucm.luisegui.dunktomic.domain.exceptions;

import lombok.Getter;

@Getter
public abstract class DomainException extends RuntimeException
{
    private final String code;
    private final String description;

    public DomainException(String code, String description, Throwable throwable) {
        super(description, throwable);
        this.code = code;
        this.description = description;
    }

    public DomainException(String code, String description) {
        super(description);
        this.code = code;
        this.description = description;
    }

}
