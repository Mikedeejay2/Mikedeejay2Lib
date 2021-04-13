package com.mikedeejay2.mikedeejay2lib.util.file;

import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Utility class for files
 *
 * @author Mikedeejay2
 */
public final class FileUtil
{
    /**
     * Get the file extension of a file. Uses {@link FilenameUtils} to get the extension but removes
     * the <tt>.</tt> from the beginning to get just the extension and nothing else.
     *
     * @param file The file to get the extension from
     * @return The file extension. If the file has no extension, <tt>""</tt> is returned
     */
    public static String getFileExtension(File file)
    {
        String name = file.getName();
        String rawExtension = FilenameUtils.getExtension(name);
        String finalExtension = rawExtension.replaceFirst(".", "");
        return finalExtension;
    }
}
