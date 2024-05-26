package es.ucm.luisegui.dunktomic.infrastructure.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtEntity
{
    private UUID clubId;
    private String name;
    private String type;

    public static class Field
    {
        public static final String TABLE = "Court";
        public static final String CLUB_ID = "club_id";
        public static final String NAME = "name";
        public static final String TYPE = "type";
    }
}
