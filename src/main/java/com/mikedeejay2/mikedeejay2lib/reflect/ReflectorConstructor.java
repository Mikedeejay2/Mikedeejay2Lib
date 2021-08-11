package com.mikedeejay2.mikedeejay2lib.reflect;

import java.lang.reflect.Constructor;

/**
 * A wrapper class for {@link Constructor} for simpler reflection needs
 *
 * @param <T> The parent class type
 * @author Mikedeejay2
 */
public class ReflectorConstructor<T>
{
    /**
     * The parent {@link ReflectorClass}
     */
    protected final ReflectorClass<T> clazz;

    /**
     * The reference {@link Constructor}
     */
    protected final Constructor<T> constructor;

    /**
     * Construct a new <code>ReflectorConstructor</code>
     *
     * @param clazz       The parent {@link ReflectorClass}
     * @param constructor The reference {@link Constructor}
     */
    public ReflectorConstructor(ReflectorClass<T> clazz, Constructor<T> constructor)
    {
        this.clazz = clazz;
        this.constructor = constructor;
    }

    /**
     * Construct a new <code>ReflectorConstructor</code>
     *
     * @param clazz          The parent {@link ReflectorClass}
     * @param parameterTypes The array of parameter types of the constructor
     */
    public ReflectorConstructor(ReflectorClass<T> clazz, Class<?>... parameterTypes)
    {
        this.clazz = clazz;
        Constructor<T> constructor;
        try
        {
            constructor = clazz.get().getConstructor(parameterTypes);
        }
        catch(NoSuchMethodException e)
        {
            e.printStackTrace();
            constructor = null;
        }
        this.constructor = constructor;
    }

    /**
     * Construct a new instance based off of this constructor
     *
     * @param initargs The arguments to pass to the constructor for construction. Arguments must match the parameter
     *                 types in type and amount of arguments.
     * @return A new instance of type T
     */
    public T newInstance(Object... initargs)
    {
        try
        {
            return constructor.newInstance(initargs);
        }
        catch(ReflectiveOperationException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the parent {@link ReflectorClass} of this field
     *
     * @return The parent <code>ReflectorClass</code>
     */
    public ReflectorClass<T> parentClass()
    {
        return clazz;
    }
}
