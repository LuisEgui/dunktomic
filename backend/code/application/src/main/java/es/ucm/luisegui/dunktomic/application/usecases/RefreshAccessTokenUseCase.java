package es.ucm.luisegui.dunktomic.application.usecases;

import es.ucm.luisegui.dunktomic.domain.entities.Token;
import es.ucm.luisegui.dunktomic.domain.exceptions.UnauthorizedException;
import es.ucm.luisegui.dunktomic.domain.repositories.AuthRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RefreshAccessTokenUseCase implements UseCase<String, Token>
{
    private final AuthRepository authRepository;

    public RefreshAccessTokenUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public Token execute(String input) {
        Optional<Token> token = authRepository.refreshAccessToken(input);

        if (token.isPresent())
            return token.get();
        else
            throw new UnauthorizedException();
    }
}
