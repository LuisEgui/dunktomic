package es.ucm.luisegui.dunktomic.domain.repositories;

import es.ucm.luisegui.dunktomic.domain.entities.Token;
import java.util.Optional;

public interface AuthRepository
{
    Optional<Token> grantAccessToken(String email, String password);
}
