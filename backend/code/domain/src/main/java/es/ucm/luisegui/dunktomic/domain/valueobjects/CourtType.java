package es.ucm.luisegui.dunktomic.domain.valueobjects;

import lombok.Getter;

@Getter
public enum CourtType
{
    INDOOR("indoor"),
    OUTDOOR("outdoor"),
    THREE_FOR_THREE("3x3");

    private final String value;

    CourtType(String value) {
        this.value = value;
    }

    public static CourtType fromValue(String value) {
        for (CourtType type : CourtType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown court type: " + value);
    }
}
