package es.ucm.luisegui.dunktomic.domain.valueobjects;

import lombok.Getter;

@Getter
public enum CourtPositions
{
    PG("Point Guard"),
    SG("Shooting Guard"),
    SF("Small Forward"),
    PF("Power Forward"),
    C("Center");

    private final String value;

    CourtPositions(String value) {
        this.value = value;
    }

    public static CourtPositions fromValue(String value) {
        for (CourtPositions position : CourtPositions.values()) {
            if (position.value.equalsIgnoreCase(value)) {
                return position;
            }
        }
        throw new IllegalArgumentException("Unknown court position: " + value);
    }
}
