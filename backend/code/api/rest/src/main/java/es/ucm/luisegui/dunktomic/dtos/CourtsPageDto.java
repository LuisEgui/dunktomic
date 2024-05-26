package es.ucm.luisegui.dunktomic.dtos;

import es.ucm.luisegui.dunktomic.domain.entities.Court;
import es.ucm.luisegui.dunktomic.rest.dtos.CourtsPage;
import es.ucm.luisegui.dunktomic.rest.dtos.PageMetadata;
import org.springframework.data.domain.Page;
import java.util.List;

public class CourtsPageDto
{
    public static CourtsPage toModel(Page<Court> courtPage) {
        List<es.ucm.luisegui.dunktomic.rest.dtos.Court> data = courtPage.getContent().stream().map(CourtDto::toModel).toList();
        PageMetadata metadata = PageMetadataDto.toMetadataModel(courtPage);
        return new CourtsPage().data(data).meta(metadata);
    }
}
