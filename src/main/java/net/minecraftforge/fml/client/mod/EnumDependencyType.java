package net.minecraftforge.fml.client.mod;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum EnumDependencyType {

    COMPARE_MAJOR   (0, "compare_major", "+"),
    COMPARE_MINOR   (1, "compare_minor", "++"),
    COMPARE_PATCH   (2, "compare_patch", "+++");

    public static final EnumDependencyType[] VALUES = {COMPARE_MAJOR, COMPARE_MINOR, COMPARE_PATCH};
    private int index;
    private String name;
    private String code;

    EnumDependencyType(int index, String name, String code) {
        this.index = index;
        this.name = name;
        this.code = code;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Nullable
    public static EnumDependencyType fromCode(String code) {
        return Arrays.stream(VALUES).filter(t -> t.code.equals(code)).findFirst().get();
    }

}