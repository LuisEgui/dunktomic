package es.ucm.luisegui.dunktomic.application.dtos;

import es.ucm.luisegui.dunktomic.domain.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlayerDto
{
    private Player player;
    private String password;
}
