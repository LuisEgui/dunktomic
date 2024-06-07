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
    private String GRANT_TYPE = "";

    public AuthRepositoryImpl(AuthApi authApi) {
        this.authApi = authApi;
    }

    @Override
    public Optional<Token> grantAccessToken(String email, String password) {
        GRANT_TYPE = "password";
        TokenMapper tokenMapper = new TokenMapper();
        try {
            return authApi.exchangeToken(REALM, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, null, email, password, null)
                .map(tokenMapper::toEntity).blockOptional();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Token> refreshAccessToken(String refreshToken) {
        GRANT_TYPE = "refresh_token";
        TokenMapper tokenMapper = new TokenMapper();
        try {
            return authApi.exchangeToken(REALM, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, null, null, null, refreshToken)
                .map(tokenMapper::toEntity).blockOptional();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
