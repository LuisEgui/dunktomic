package es.ucm.luisegui.dunktomic.infrastructure.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity
{
    @Id
    private UUID id;
    private String path;
    private String mimeType;

    public ImageEntity(UUID clubImage) {
        this.id = clubImage;
    }

    public static class Field
    {
        public static final String TABLE = "Image";
        public static final String ID = "id";
        public static final String PATH = "path";
        public static final String MIME_TYPE = "mime_type";
    }
}
