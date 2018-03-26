package net.minecraftforge.fml.client.mod;

import javax.annotation.Nullable;

public class Version {

    private int major;
    private int minor;
    private int patch;

    private Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Version) {
            Version version = (Version)o;
            return major == version.major && minor == version.minor && patch == version.patch;
        }
        else if(o instanceof String) {
            Version version = parseVersion((String)o);
            return major == version.major && minor == version.minor && patch == version.patch;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(major);
        builder.append('.');
        builder.append(minor);
        builder.append('.');
        builder.append(patch);
        return builder.toString();
    }

    public static Version parseVersion(String s) throws VersionParseException {
        String[] array = s.split("\\.");

        try {
            int major = Integer.parseInt(array[0]);
            int minor = Integer.parseInt(array[1]);
            int patch = Integer.parseInt(array[2]);
            return new Version(major, minor, patch);
        }
        catch(NumberFormatException e) {
            throw new VersionParseException("Error parsing version, '%s' is not a valid version format!", s);
        }
    }

    public static Version fromNumbers(int major, int minor, int patch) {
        return new Version(major, minor, patch);
    }

    public static boolean compare(@Nullable EnumDependencyType type, Version version1, Version version2) {
        if(type != null) {
            switch(type) {
                case COMPARE_MAJOR:
                    return version1.major == version2.major;
                case COMPARE_MINOR:
                    return version1.major == version2.major && version1.minor == version2.minor;
                case COMPARE_PATCH:
                    return version1.major == version2.major && version1.minor == version2.minor && version1.patch == version2.patch;
            }
        }
        else {
            return version1.equals(version2);
        }

        return false;
    }

    private static class VersionParseException extends RuntimeException {

        private VersionParseException(String message, Object... params) {
            super(String.format(message, params));
        }

    }

}