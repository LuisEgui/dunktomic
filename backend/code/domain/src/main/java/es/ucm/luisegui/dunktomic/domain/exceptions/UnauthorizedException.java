package es.ucm.luisegui.dunktomic.domain.exceptions;

public class UnauthorizedException extends DomainException
{
    public UnauthorizedException() {
        super("UNAUTHORIZED", "Unauthorized");
    }

}
