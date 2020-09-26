package com.mikedeejay2.mikedeejay2lib.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple util class for modifying arrays.
 *
 * @author Mikedeejay2
 */
public final class ArrayModifier
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
    public static <T> T[] trimArray(T[] arr, Class<T> clazz, int startIndex)
    {
        return trimArray(arr, clazz, startIndex, arr.length);
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
    public static <T> T[] trimArray(T[] arr, Class<T> clazz, int startIndex, int endIndex)
    {
        ArrayList<T> newList = new ArrayList<>();
        for(int i = startIndex; i < endIndex; i++)
        {
            T cur = arr[i];
            newList.add(cur);
        }
        return newList.toArray((T[])Array.newInstance(clazz, newList.size()));
    }

    /**
     * Convert a String array to an int array
     *
     * @param arr The array to convert
     * @return The int array
     */
    public static int[] toIntArray(String[] arr)
    {
        int[] newArr = new int[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            newArr[i] = Integer.parseInt(arr[i]);
        }
        return newArr;
    }

    /**
     * Convert a String array to a double array
     *
     * @param arr The array to convert
     * @return The double array
     */
    public static double[] toDoubleArray(String[] arr)
    {
        double[] newArr = new double[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            newArr[i] = Double.parseDouble(arr[i]);
        }
        return newArr;
    }

    /**
     * Convert a String array to a float array
     *
     * @param arr The array to convert
     * @return The float array
     */
    public static float[] toFloatArray(String[] arr)
    {
        float[] newArr = new float[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            newArr[i] = Float.parseFloat(arr[i]);
        }
        return newArr;
    }

    /**
     * Convert a String array to a long array
     *
     * @param arr The array to convert
     * @return The long array
     */
    public static long[] toLongArray(String[] arr)
    {
        long[] newArr = new long[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            newArr[i] = Long.parseLong(arr[i]);
        }
        return newArr;
    }

    /**
     * Convert a String array to a boolean array
     *
     * @param arr The array to convert
     * @return The boolean array
     */
    public static boolean[] toBooleanArray(String[] arr)
    {
        boolean[] newArr = new boolean[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            newArr[i] = Boolean.parseBoolean(arr[i]);
        }
        return newArr;
    }

    /**
     * Extremely simple method for converting an array to a list.
     *
     * @param arr The array to convert
     * @param <T> The type to be used
     * @return The list of the array
     */
    public static <T> List<T> toList(T[] arr)
    {
        return Arrays.asList(arr);
    }

    /**
     * Converts a String array to an Integer List
     *
     * @param arr Array to convert
     * @return A new Integer list containing data from array
     */
    public static List<Integer> toIntList(String[] arr)
    {
        List<Integer> list = new ArrayList<>();
        for(String str : arr)
        {
            list.add(Integer.parseInt(str));
        }
        return list;
    }

    /**
     * Converts a String array to a Double List
     *
     * @param arr Array to convert
     * @return A new Double list containing data from array
     */
    public static List<Double> toDoubleList(String[] arr)
    {
        List<Double> list = new ArrayList<>();
        for(String str : arr)
        {
            list.add(Double.parseDouble(str));
        }
        return list;
    }

    /**
     * Converts a String array to a Float List
     *
     * @param arr Array to convert
     * @return A new Float list containing data from array
     */
    public static List<Float> toFloatList(String[] arr)
    {
        List<Float> list = new ArrayList<>();
        for(String str : arr)
        {
            list.add(Float.parseFloat(str));
        }
        return list;
    }

    /**
     * Converts a String array to a Long List
     *
     * @param arr Array to convert
     * @return A new Long list containing data from array
     */
    public static List<Long> toLongList(String[] arr)
    {
        List<Long> list = new ArrayList<>();
        for(String str : arr)
        {
            list.add(Long.parseLong(str));
        }
        return list;
    }

    /**
     * Converts a String array to a Boolean List
     *
     * @param arr Array to convert
     * @return A new Boolean list containing data from array
     */
    public static List<Boolean> toBooleanList(String[] arr)
    {
        List<Boolean> list = new ArrayList<>();
        for(String str : arr)
        {
            list.add(Boolean.parseBoolean(str));
        }
        return list;
    }
}
