package es.ucm.luisegui.dunktomic.domain.entities;

import es.ucm.luisegui.dunktomic.domain.valueobjects.MimeType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Image
{
    @NotBlank
    private String path;
    @NotBlank
    private MimeType mimeType;
}
