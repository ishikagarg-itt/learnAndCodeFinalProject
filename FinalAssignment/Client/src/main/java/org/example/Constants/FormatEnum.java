package org.example.Constants;
public enum FormatEnum {
    JSON("JSON"),
    XML("XML");
    private String formatName;

    FormatEnum(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatName() {
        return formatName;
    }

    public static FormatEnum fromFormatName(String name) {
        for (FormatEnum formatEnum : FormatEnum.values()) {
            if (formatEnum.formatName.equalsIgnoreCase(name)) {
                return formatEnum;
            }
        }
        return null;
    }
}

