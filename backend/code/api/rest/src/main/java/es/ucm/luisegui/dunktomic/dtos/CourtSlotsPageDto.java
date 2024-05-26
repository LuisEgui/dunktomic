package es.ucm.luisegui.dunktomic.dtos;


import es.ucm.luisegui.dunktomic.domain.entities.CourtSlot;
import es.ucm.luisegui.dunktomic.rest.dtos.CourtSlotsPage;
import es.ucm.luisegui.dunktomic.rest.dtos.PageMetadata;
import org.springframework.data.domain.Page;
import java.util.List;

public class CourtSlotsPageDto
{
    public static CourtSlotsPage toModel(Page<CourtSlot> courtSlotPage) {
        List<es.ucm.luisegui.dunktomic.rest.dtos.CourtSlot> data = courtSlotPage.getContent().stream().map(CourtSlotDto::toModel).toList();
        PageMetadata metadata = PageMetadataDto.toMetadataModel(courtSlotPage);
        return new CourtSlotsPage().data(data).meta(metadata);
    }
}
