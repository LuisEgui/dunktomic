package es.ucm.luisegui.dunktomic.infrastructure.repository;

import es.ucm.luisegui.dunktomic.domain.entities.Token;
import es.ucm.luisegui.dunktomic.domain.repositories.AuthRepository;
import es.ucm.luisegui.dunktomic.infrastructure.keycloakclient.api.AuthApi;
import es.ucm.luisegui.dunktomic.infrastructure.repository.mappers.TokenMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class AuthRepositoryImpl implements AuthRepository
{
    private final AuthApi authApi;
    private static final String REALM = "master";
    @Value("${keycloak.client-id}")
    private String CLIENT_ID;
    @Value("${keycloak.client-secret}")
    private String CLIENT_SECRET;
    private static final String GRANT_TYPE = "password";

    public AuthRepositoryImpl(AuthApi authApi) {
        this.authApi = authApi;
    }

    @Override
    public Optional<Token> grantAccessToken(String email, String password) {
        TokenMapper tokenMapper = new TokenMapper();
        try {
            return authApi.exchangeToken(REALM, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, null, email, password)
                .map(tokenMapper::toEntity).blockOptional();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
