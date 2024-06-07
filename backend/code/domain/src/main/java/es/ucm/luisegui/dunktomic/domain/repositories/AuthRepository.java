package es.ucm.luisegui.dunktomic.domain.repositories;

import es.ucm.luisegui.dunktomic.domain.entities.Token;
import java.util.Optional;

public interface AuthRepository
{
    Optional<Token> grantAccessToken(String email, String password);

    Optional<Token> refreshAccessToken(String refreshToken);

    void revokeAccessToken(String refreshToken);
}
