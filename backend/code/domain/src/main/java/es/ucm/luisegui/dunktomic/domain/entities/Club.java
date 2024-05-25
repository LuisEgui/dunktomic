package es.ucm.luisegui.dunktomic.domain.entities;

import es.ucm.luisegui.dunktomic.domain.valueobjects.EntityId;
import lombok.Data;

@Data
public class Club
{
    private EntityId id;
    private String name;
    private String district;
    private String postalCode;
    private String streetAddress;
    private Float latitude;
    private Float longitude;
    private Image image;

}
