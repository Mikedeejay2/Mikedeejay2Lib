package com.mikedeejay2.mikedeejay2lib.util.file;

import java.io.File;

/**
 * Utility class for files
 *
 * @author Mikedeejay2
 */
public final class FileUtil {
    /**
     * Get the file extension of a file.
     *
     * @param file The file to get the extension from
     * @return The file extension. If the file has no extension, <code>""</code> is returned
     */
    public static String getFileExtension(File file) {
        return getFileExtension(file.getName());
    }

    /**
     * Get the file extension of a file.
     *
     * @param name The file name to get the extension from
     * @return The extension String, <code>""</code> if no extension, <code>null</code> if original file name was <code>null</code>
     */
    public static String getFileExtension(String name) {
        if (name == null) return null;
        int index = indexOfExtension(name);
        return index == -1 ? "" : name.substring(index + 1);
    }

    /**
     * Get the index of the file extension for a file name
     *
     * @param name The file name to get the extension from
     * @return The index of the file extension, <code>-1</code> if file name is <code>null</code> or no extension exists
     */
    public static int indexOfExtension(String name) {
        if (name == null) return -1;
        int extensionPos = name.lastIndexOf('.');
        int lastSeparator = indexOfLastSeparator(name);
        return lastSeparator > extensionPos ? -1 : extensionPos;
    }

    /**
     * Get the index of the last file path separator  for a file name
     *
     * @param name The file name to get the index from
     * @return The index of the last file separator <code>-1</code> if file name is <code>null</code>
     */
    public static int indexOfLastSeparator(String name) {
        if (name == null) return -1;
        int pos1 = name.lastIndexOf('/');
        int pos2 = name.lastIndexOf('\\');
        return Math.max(pos1, pos2);
    }
}
