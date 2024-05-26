package es.ucm.luisegui.dunktomic.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class FindClubCourtsDto
{
    private String id;
    private Pageable pageable;
}
