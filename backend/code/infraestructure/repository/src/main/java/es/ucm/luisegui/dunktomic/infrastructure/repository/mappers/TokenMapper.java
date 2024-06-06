package es.ucm.luisegui.dunktomic.infrastructure.repository.mappers;

import es.ucm.luisegui.dunktomic.domain.entities.Token;
import lombok.Data;

@Data
public class TokenMapper
{
    public Token toEntity(es.ucm.luisegui.dunktomic.infrastructure.keycloakclient.model.Token token)
    {
        Token ret = new Token();
        ret.setAccessToken(token.getAccessToken());
        ret.setTokenType(token.getTokenType());
        ret.setRefreshToken(token.getRefreshToken());
        ret.setExpiresIn(token.getExpiresIn());
        ret.setRefreshExpiresIn(token.getRefreshExpiresIn());
        return ret;
    }
}
