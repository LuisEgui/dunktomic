package es.ucm.luisegui.dunktomic.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class FindClubsFilters
{
    private String name;
    private String district;
    private String postalCode;
    private String streetAddress;
    private Pageable pageable;
}
