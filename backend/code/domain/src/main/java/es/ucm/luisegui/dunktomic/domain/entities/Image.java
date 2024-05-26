package es.ucm.luisegui.dunktomic.domain.entities;

import es.ucm.luisegui.dunktomic.domain.valueobjects.MimeType;
import lombok.Data;

@Data
public class Image
{
    private String path;
    private MimeType mimeType;
}
