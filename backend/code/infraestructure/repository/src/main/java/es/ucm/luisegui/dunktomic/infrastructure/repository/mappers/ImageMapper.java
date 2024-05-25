package es.ucm.luisegui.dunktomic.infrastructure.repository.mappers;

import es.ucm.luisegui.dunktomic.domain.entities.Image;
import es.ucm.luisegui.dunktomic.domain.valueobjects.MimeType;
import es.ucm.luisegui.dunktomic.infrastructure.database.models.ImageEntity;
import lombok.Data;

@Data
public class ImageMapper
{
    public Image toEntity(ImageEntity imageEntity)
    {
        Image image = new Image();
        if (imageEntity.getPath() != null)
            image.setPath(imageEntity.getPath());
        else
            image.setPath("");
        if (imageEntity.getMimeType() != null)
            image.setMimeType(MimeType.fromValue(imageEntity.getMimeType()));
        else
            image.setMimeType(MimeType.JPEG);
        return image;
    }
}
