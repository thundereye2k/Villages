package com.stefthedev.villages.utilities.general;

public class Hook {

    private final String name;
    private boolean enabled = false;

    public Hook(String name) {
        this.name = name;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
