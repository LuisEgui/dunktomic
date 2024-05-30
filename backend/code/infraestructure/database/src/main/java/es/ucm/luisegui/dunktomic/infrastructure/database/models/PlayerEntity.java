package es.ucm.luisegui.dunktomic.infrastructure.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerEntity
{
    @Id
    private UUID id;
    private String email;
    private String name;
    private String role;
    private ImageEntity image;
    private Set<String> courtPositions;
    private Float level;

    public static class Field {
        public static final String TABLE = "Player";
        public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String NAME = "name";
        public static final String ROLE = "role";
        public static final String IMAGE = "user_image";
        public static final String COURT_POSITIONS = "positions_on_court";
        public static final String LEVEL = "level";
    }
}
