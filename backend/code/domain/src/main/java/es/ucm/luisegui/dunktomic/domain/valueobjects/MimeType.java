package es.ucm.luisegui.dunktomic.domain.valueobjects;

import lombok.Getter;

@Getter
public enum MimeType
{
    JPEG("image/jpeg"),
    JPG("image/jpg"),
    PNG("image/png");

    private final String value;

    MimeType(String value) {
        this.value = value;
    }

    public static MimeType fromValue(String value) {
        for (MimeType type : MimeType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown mime type: " + value);
    }
}
