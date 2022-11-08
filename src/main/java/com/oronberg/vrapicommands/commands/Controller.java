package com.oronberg.vrapicommands.commands;

public enum Controller {
    MAIN("mainhand"),
    OFF("offhand"),
    HMD("hmd");

    private final String name;

    Controller(String name) {
        this.name = name;
    }

    public String getName() { return this.name; }
}
