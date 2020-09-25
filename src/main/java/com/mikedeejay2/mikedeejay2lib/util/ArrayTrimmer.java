package com.mikedeejay2.mikedeejay2lib.util;

import java.util.ArrayList;

/**
 * A simple util class for trimming and modifying arrays.
 *
 * @author Mikedeejay2
 */
public final class ArrayTrimmer
{
    /**
     * Get an array as a String, each index separated by a space.
     *
     * @param arr The array to convert
     * @return The array converted to a String
     */
    public static String getAsString(String[] arr)
    {
        return getAsString(arr, 0, arr.length);
    }

    /**
     * Get an array as a String, each index separated by a space.
     *
     * @param arr The array to convert
     * @param startIndex The index to start at
     * @return The array converted to a String
     */
    public static String getAsString(String[] arr, int startIndex)
    {
        return getAsString(arr, startIndex, arr.length);
    }

    /**
     * Get an array as a String, each index separated by a space.
     *
     * @param arr The array to convert
     * @param startIndex The index to start at
     * @param endIndex The index to end at
     * @return The array converted to a String
     */
    public static String getAsString(String[] arr, int startIndex, int endIndex)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = startIndex; i < endIndex; i++)
        {
            if(stringBuilder.toString().isEmpty()) stringBuilder.append(arr[i]);
            else stringBuilder.append(" ").append(arr[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Trim an array down to a specific start and end index
     *
     * @param arr The array to trim
     * @param startIndex The starting index to keep
     * @param <T> The type of the array
     * @return The trimmed array
     */
    public static <T> T[] trimArray(T[] arr, int startIndex)
    {
        return trimArray(arr, startIndex, arr.length);
    }

    /**
     * Trim an array down to a specific start and end index
     *
     * @param arr The array to trim
     * @param startIndex The starting index to keep
     * @param endIndex The ending index to keep (<tt>i < endIndex</tt>)
     * @param <T> The type of the array
     * @return The trimmed array
     */
    public static <T> T[] trimArray(T[] arr, int startIndex, int endIndex)
    {
        ArrayList<T> newList = new ArrayList<>();
        for(int i = startIndex; i < endIndex; i++)
        {
            T cur = arr[i];
            newList.add(cur);
        }
        return newList.toArray(null);
    }
}
