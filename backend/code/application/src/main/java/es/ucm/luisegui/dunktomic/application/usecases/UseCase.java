package es.ucm.luisegui.dunktomic.application.usecases;

public interface UseCase<I, O>
{
    O execute(I input);
}
