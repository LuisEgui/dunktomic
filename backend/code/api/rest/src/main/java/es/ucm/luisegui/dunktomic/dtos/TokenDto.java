package es.ucm.luisegui.dunktomic.dtos;

import es.ucm.luisegui.dunktomic.rest.dtos.Token;

public class TokenDto
{
    public static Token toModel(es.ucm.luisegui.dunktomic.domain.entities.Token token) {
        return token != null ?
            new Token()
                .accessToken(token.getAccessToken())
                .tokenType(token.getTokenType())
                .expiresIn(token.getExpiresIn())
                .refreshToken(token.getRefreshToken())
                .refreshExpiresIn(token.getRefreshExpiresIn()):
                // TODO: Remove scope out of Token response
            null;
    }
}
