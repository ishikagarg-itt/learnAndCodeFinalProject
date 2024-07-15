package org.example.Constants;

public enum ResponseCodeEnum {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");
    private String responseCodeName;

    @Override
    public String toString() {
        return responseCodeName;
    }

    ResponseCodeEnum(String responseCodeName) {
        this.responseCodeName = responseCodeName;
    }

    public String getResponseCodeName() {
        return responseCodeName;
    }

    public static ResponseCodeEnum fromResponseCodeName(String name) {
        for (ResponseCodeEnum responseCodeEnum : ResponseCodeEnum.values()) {
            if (responseCodeEnum.responseCodeName.equalsIgnoreCase(name)) {
                return responseCodeEnum;
            }
        }
        return null;
    }
}
