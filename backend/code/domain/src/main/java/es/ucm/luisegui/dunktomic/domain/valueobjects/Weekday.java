package es.ucm.luisegui.dunktomic.domain.valueobjects;

import lombok.Getter;

@Getter
public enum Weekday
{
    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    private final String value;

    Weekday(String value) {
        this.value = value;
    }

    public static Weekday fromValue(String value) {
        for (Weekday day : Weekday.values()) {
            if (day.value.equalsIgnoreCase(value)) {
                return day;
            }
        }
        throw new IllegalArgumentException("Unknown weekday: " + value);
    }

}
