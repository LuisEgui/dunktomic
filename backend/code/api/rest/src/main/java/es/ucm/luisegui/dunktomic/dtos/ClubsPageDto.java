package es.ucm.luisegui.dunktomic.dtos;

import es.ucm.luisegui.dunktomic.domain.entities.Club;
import es.ucm.luisegui.dunktomic.rest.dtos.ClubsPage;
import es.ucm.luisegui.dunktomic.rest.dtos.PageMetadata;
import org.springframework.data.domain.Page;
import java.util.List;

public class ClubsPageDto
{
    public static ClubsPage toModel(Page<Club> clubPage) {
        List<es.ucm.luisegui.dunktomic.rest.dtos.Club> data = clubPage.getContent().stream().map(ClubDto::toModel).toList();
        PageMetadata metadata = PageMetadataDto.toMetadataModel(clubPage);
        return new ClubsPage().data(data).meta(metadata);
    }
}
