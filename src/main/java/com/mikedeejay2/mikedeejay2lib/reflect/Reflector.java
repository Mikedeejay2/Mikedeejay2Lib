package com.mikedeejay2.mikedeejay2lib.reflect;

/**
 * Static entrypoint class for reflecting with Mikedeejay2Lib.
 *
 * @see Reflector#of(String)
 * @see Reflector#of(Class)
 * @author Mikedeejay2
 */
public final class Reflector
{
    /**
     * Reflector cannot be constructed, it is a static class.
     */
    private Reflector()
    {
        throw new UnsupportedOperationException("Cannot initialize static class Reflector");
    }

    /**
     * Get a {@link ReflectorClass} based off of a <code>Class</code>
     *
     * @param clazz The <code>Class</code> to reference
     * @param <T>   The class type
     * @return A new {@link ReflectorClass}
     */
    public static <T> ReflectorClass<T> of(Class<T> clazz)
    {
        return new ReflectorClass<>(clazz);
    }


    /**
     * Get a {@link ReflectorClass} based off of the class's name
     *
     * @param className The fully qualified name of the class to get
     * @return A new {@link ReflectorClass}
     */
    public static ReflectorClass<?> of(String className)
    {
        Class<?> clazz;
        try
        {
            clazz = Class.forName(className);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        return of(clazz);
    }
}
