package es.ucm.luisegui.dunktomic.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordCredentialsDto
{
    private String email;
    private String password;
}
