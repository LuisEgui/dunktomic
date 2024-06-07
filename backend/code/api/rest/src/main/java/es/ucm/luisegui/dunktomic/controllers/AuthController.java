package es.ucm.luisegui.dunktomic.controllers;

import es.ucm.luisegui.dunktomic.application.dtos.PasswordCredentialsDto;
import es.ucm.luisegui.dunktomic.application.usecases.RefreshAccessTokenUseCase;
import es.ucm.luisegui.dunktomic.application.usecases.RetriveAccessTokenUseCase;
import es.ucm.luisegui.dunktomic.dtos.TokenDto;
import es.ucm.luisegui.dunktomic.rest.AuthApi;
import es.ucm.luisegui.dunktomic.rest.dtos.ExchangeTokenRequest;
import es.ucm.luisegui.dunktomic.rest.dtos.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi
{
    private final RetriveAccessTokenUseCase retriveAccessTokenUseCase;
    private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;

    public AuthController(RetriveAccessTokenUseCase retriveAccessTokenUseCase, RefreshAccessTokenUseCase refreshAccessTokenUseCase) {
        this.retriveAccessTokenUseCase = retriveAccessTokenUseCase;
        this.refreshAccessTokenUseCase = refreshAccessTokenUseCase;
    }

    @Override
    public ResponseEntity<Token> getAuthToken(String acceptLanguage, String grantType, String email, String password) {
        PasswordCredentialsDto passwordCredentials = new PasswordCredentialsDto(email, password);
        es.ucm.luisegui.dunktomic.domain.entities.Token token = retriveAccessTokenUseCase.execute(passwordCredentials);
        return ResponseEntity.ok().body(TokenDto.toModel(token));
    }

    @Override
    public ResponseEntity<Token> exchangeToken(ExchangeTokenRequest exchangeTokenRequest) {
        String refreshToken = exchangeTokenRequest.getRefreshToken();
        es.ucm.luisegui.dunktomic.domain.entities.Token token = refreshAccessTokenUseCase.execute(refreshToken);
        return ResponseEntity.ok().body(TokenDto.toModel(token));
    }
}
