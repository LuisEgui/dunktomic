package es.ucm.luisegui.dunktomic.application.usecases;

import es.ucm.luisegui.dunktomic.domain.exceptions.UnauthorizedException;
import es.ucm.luisegui.dunktomic.domain.repositories.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCase implements UseCase<String, Void>
{
    private final AuthRepository authRepository;

    public LogoutUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public Void execute(String input) {
        try {
            authRepository.revokeAccessToken(input);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
        return null;
    }
}
