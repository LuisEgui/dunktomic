package es.ucm.luisegui.dunktomic.dtos;

import es.ucm.luisegui.dunktomic.rest.dtos.PageMetadata;
import org.springframework.data.domain.Page;

public class PageMetadataDto
{
    public static <T> PageMetadata toMetadataModel(Page<T> pageMetadata) {
        return new PageMetadata(
            pageMetadata.getNumber(),
            pageMetadata.getTotalPages(),
            pageMetadata.getSize(),
            (int) pageMetadata.getTotalElements()
        );
    }
}
