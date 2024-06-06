package es.ucm.luisegui.dunktomic.application.usecases;

import es.ucm.luisegui.dunktomic.application.dtos.PasswordCredentialsDto;
import es.ucm.luisegui.dunktomic.domain.entities.Token;
import es.ucm.luisegui.dunktomic.domain.exceptions.UnauthorizedException;
import es.ucm.luisegui.dunktomic.domain.repositories.AuthRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RetriveAccessTokenUseCase implements UseCase<PasswordCredentialsDto, Token>
{
    private final AuthRepository authRepository;

    public RetriveAccessTokenUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public Token execute(PasswordCredentialsDto input) {
        String email = input.getEmail();
        String password = input.getPassword();
        Optional<Token> token = authRepository.grantAccessToken(email, password);

        if (token.isPresent())
            return token.get();
        else
            throw new UnauthorizedException();

    }
}
