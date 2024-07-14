package org.example.Constants;
public enum CommandEnum {
    VIEW_DISCARD_ITEMS("VIEW_DISCARD_ITEMS"),
    GET_ALL_ITEM("GET_ALL_ITEM"),
    DELETE_ITEM("DELETE_ITEM"),
    GET_ITEM("GET_ITEM"),
    UPDATE_ITEM("UPDATE_ITEM"),
    ADD_ITEM("ADD_ITEM"),
    UPDATE_PROFILE("UPDATE_PROFILE"),
    GIVE_DISCARD_ITEM_RATING("GIVE_DISCARD_ITEM_RATING"),
    VIEW_NOTIFICATIONS("VIEW_NOTIFICATIONS"),
    GIVE_RATING("GIVE_RATING"),
    CHOOSE_ITEMS("CHOOSE_ITEMS"),
    GET_ROLL_OUT_MENU("GET_ROLL_OUT_MENU"),
    LOGIN("LOGIN"),
    ROLL_OUT_MENU("ROLL_OUT_MENU"),
    ASK_FEEDBACK("ASK_FEEDBACK"),
    GET_RECOMMENDATION("GET-RECOMMENDATION");
    private String commandName;

    CommandEnum(String formatName) {
        this.commandName = formatName;
    }

    public String getCommandName() {
        return commandName;
    }

    public static CommandEnum fromCommandName(String name) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.commandName.equalsIgnoreCase(name)) {
                return commandEnum;
            }
        }
        return null;
    }
}