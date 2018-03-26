package net.minecraftforge.fml.client.mod;

import java.util.Arrays;

public enum EnumModState {

    ENABLED     (0, "enabled"),
    DISABLED    (1, "disabled");

    public static final EnumModState[] VALUES = {};
    private int index;
    private String name;

    EnumModState(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static EnumModState fromName(String name) {
        return Arrays.stream(VALUES).filter(s -> s.name.equals(name)).findFirst().get();
    }

}