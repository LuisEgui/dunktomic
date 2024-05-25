package es.ucm.luisegui.dunktomic.dtos;


import es.ucm.luisegui.dunktomic.rest.dtos.Image;

public class ImageDto
{
    public Image toModel(es.ucm.luisegui.dunktomic.domain.entities.Image image) {
        return image != null ?
            new Image()
                .path(image.getPath())
                .mimeType(Image.MimeTypeEnum.valueOf(image.getMimeType().toString())) :
            null;
    }
}
