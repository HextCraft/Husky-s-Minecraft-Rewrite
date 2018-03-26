package net.minecraftforge.fml.client.mod;

public class Dependency {

    private String modid;
    private Version version;
    private EnumDependencyType type;

    public Dependency(String modid, Version version, EnumDependencyType type) {
        this.modid = modid;
        this.version = version;
        this.type = type;
    }

    public String getModID() {
        return modid;
    }

    public Version getVersion() {
        return version;
    }

    public EnumDependencyType getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(modid);
        builder.append('|');
        builder.append(version.toString());
        builder.append('|');
        builder.append(type.getCode());
        return builder.toString();
    }

}