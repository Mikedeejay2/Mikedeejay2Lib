package com.mikedeejay2.mikedeejay2lib.util.version;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A util for getting the Minecraft server's version and converting it into multiple different
 * data types.
 *
 * @author Mikedeejay2
 */
public final class MinecraftVersion {
    /**
     * The Minecraft version as a String. Example: "1.20.4"
     */
    public static final String VERSION;

    /**
     * The Minecraft version as an array. Example: [1, 20, 4]
     */
    private static final int[] VERSION_ARRAY;

    /**
     * The Minecraft version as an integer
     */
    private static final int VERSION_INT;

    /**
     * The major Minecraft version. The 1 in 1.20.4
     */
    public static final int VERSION_MAJOR;

    /**
     * The minor Minecraft version. The 20 in 1.20.4
     */
    public static final int VERSION_MINOR;

    /**
     * The patch Minecraft version. The 4 in 1.20.4
     */
    public static final int VERSION_PATCH;

    /**
     * The NMS String version that the Minecraft server is running on. Example: "v1_20_R3"
     */
    public static final String VERSION_NMS;

    static {
        VERSION = getVersion();
        VERSION_ARRAY = getVersionArray(VERSION);
        VERSION_INT = getVersionInt(VERSION);
        VERSION_MAJOR = VERSION_ARRAY[0];
        VERSION_MINOR = VERSION_ARRAY[1];
        VERSION_PATCH = VERSION_ARRAY[2];
        VERSION_NMS = getVersionNms();
    }

    /**
     * This class is static and should not be constructed
     */
    private MinecraftVersion() {
        throw new UnsupportedOperationException("Can not initialize static class MinecraftVersion");
    }

    /**
     * Calculate the version in String form. Example: "1.16.3"
     *
     * @return The calculated version String
     */
    private static String getVersion() {
        String version = Bukkit.getServer().getVersion();
        Pattern pattern = Pattern.compile("(\\(MC: )([\\d\\.]+)(\\))");
        Matcher matcher = pattern.matcher(version);
        if(matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    /**
     * Calculate the long array version. Example: [1, 16, 3]
     *
     * @return A long array version
     */
    private static int[] getVersionArray(String version) {
        String[] splitStr = version.split("\\.");
        int[] arr = new int[3];
        for(int i = 0; i < splitStr.length; ++i) {
            arr[i] = Integer.parseInt(splitStr[i]);
        }
        return arr;
    }

    private static int getVersionInt(String version) {
        int[] versionArray = getVersionArray(version);
        return versionArray[0] * 10000 + versionArray[1] * 100 + versionArray[2];
    }

    /**
     * Calculate the NMS version String. Example: "v1_20_R3"
     *
     * @return The calculated NMS version String
     */
    private static String getVersionNms() {
        String raw = Bukkit.getServer().getClass().getPackage().getName();
        String[] split = raw.split("\\.");
        return split[split.length - 1];
    }

    /**
     * Utility method to check the current Minecraft version against a given version string. The input version string
     * supports several types of fuzzy matching:
     * <ul>
     *     <li>
     *         <strong>Single matching</strong>: Match a single Minecraft version. An "x" can be used to match any patch
     *         version.
     *         <p>
     *         Examples:
     *         <ul>
     *             <li><code>1.20.4</code></li>
     *             <li><code>1.18.x</code></li>
     *             <li><code>1.20</code></li>
     *         </ul>
     *     </li>
     *     <li>
     *         <strong>Operator matching</strong>: Use a comparison operator at the beginning of the string.
     *         <p>
     *         Examples:
     *         <ul>
     *             <li><code>>=1.20.x</code></li>
     *             <li><code><1.19.4</code></li>
     *             <li><code>!=1.18.2</code></li>
     *         </ul>
     *     </li>
     *     <li>
     *         <strong>Range matching</strong>: Specify an inclusive range of Minecraft versions, oldest to newest
     *         <p>
     *         Examples:
     *         <ul>
     *             <li><code>1.19-1.20</code></li>
     *             <li><code>1.20.1-1.20.4</code></li>
     *             <li><code>1.16.x-1.18.x</code></li>
     *         </ul>
     *     </li>
     * </ul>
     *
     * @param version The input version String
     * @return Whether the Minecraft version matches the provided version
     */
    public static boolean check(String version) {
        if(version.indexOf('-') != -1) return checkRange(version);
        final char firstChar = version.charAt(0);
        switch(firstChar) {
            case '=': case '>': case '<': case '!':
                return checkOperation(version);
            default:
                return checkSingle(version);
        }
    }

    /**
     * Check a range of versions against the current Minecraft version
     *
     * @param version The input range String
     * @return Whether the provided range matches the Minecraft version
     */
    private static boolean checkRange(String version) {
        Validate.isTrue(version.indexOf('-') >= 0, "Range separator character \"-\" not found");
        Validate.isTrue(version.charAt(1) == '.', "Version not in the correct position in String");
        final String[] splitStr = version.split("-");
        Validate.isTrue(splitStr.length == 2, "Range should not have multiple separators");

        for(int i = 0; i < 2; i++) {
            String str = splitStr[i];
            if(!str.substring(str.length() - 2).equalsIgnoreCase(".x")) continue;
            splitStr[i] = str.substring(0, str.length() - 2) + (i == 1 ? ".99" : ".0");
        }
        final int minVersion = getVersionInt(splitStr[0]);
        final int maxVersion = getVersionInt(splitStr[1]);
        return VERSION_INT >= minVersion && VERSION_INT <= maxVersion;
    }

    /**
     * Check an operator against the current Minecraft version
     *
     * @param version The input operator String
     * @return Whether the provided operator matches the Minecraft version
     */
    private static boolean checkOperation(String version) {
        final String operator = version.substring(0, version.indexOf('.') - 1);
        Validate.isTrue(operator.length() < 3 && !operator.isEmpty(),
                        "Operator length is not as expected (1-2 characters in length)");

        String versionStr = version.substring(operator.length());
        // If using fuzzy x patch version, fix it
        switch(operator) {
            case "==":
            case "=":
            case "!=": versionStr = fixVersionStr(versionStr, VERSION_PATCH); break;
            case ">":
            case "<=": versionStr = fixVersionStr(versionStr, 99); break;
            case "<":
            case ">=": versionStr = fixVersionStr(versionStr, -1); break;
        }

        final int versionInt = getVersionInt(versionStr);
        switch(operator) {
            case "==":
            case "=": return VERSION_INT == versionInt;
            case ">": return VERSION_INT > versionInt;
            case ">=": return VERSION_INT >= versionInt;
            case "<": return VERSION_INT < versionInt;
            case "<=": return VERSION_INT <= versionInt;
            case "!=": return VERSION_INT != versionInt;
            default:
                throw new UnsupportedOperationException(String.format("The operation \"%s\" is not supported", operator));
        }
    }

    /**
     * Check a single version against the current Minecraft version, fuzzy x matching supported
     *
     * @param version The input version String
     * @return Whether the provided version matches the Minecraft version
     */
    private static boolean checkSingle(String version) {
        return getVersionInt(fixVersionStr(version, VERSION_PATCH)) == VERSION_INT;
    }

    /**
     * Fixes a fuzzy "x" Minecraft version, such that an input version like <code>1.20.x</code> is converted into a
     * compatible version, replacing "x" with the provided <code>newNum</code> parameter.
     * <p>
     * This method checks that x exists before performing the operation, so it is safe to call if x has not been
     * checked.
     *
     * @param version The input version String
     * @param newNum The number to replace "x"
     * @return The fixed version
     */
    private static String fixVersionStr(String version, int newNum) {
        if(!version.substring(version.length() - 1).equalsIgnoreCase("x")) return version;
        return version.substring(0, version.length() - 1) + newNum;
    }
}
