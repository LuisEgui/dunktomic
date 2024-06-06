package es.ucm.luisegui.dunktomic.domain.entities;

import lombok.Data;

@Data
public class Token
{
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Long expiresIn;
    private Long refreshExpiresIn;
}
