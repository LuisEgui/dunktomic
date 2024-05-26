package es.ucm.luisegui.dunktomic.domain.valueobjects;

import lombok.Getter;

@Getter
public enum CourtState
{
    AVAILABLE("available"),
    BOOKED("booked");

    private final String value;

    CourtState(String value) {
        this.value = value;
    }

    public static CourtState fromValue(String value) {
        for (CourtState state : CourtState.values()) {
            if (state.value.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown court state: " + value);
    }
}
