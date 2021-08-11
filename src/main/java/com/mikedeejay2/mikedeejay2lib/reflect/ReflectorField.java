package com.mikedeejay2.mikedeejay2lib.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * A wrapper class for {@link Field} for simpler reflection needs
 *
 * @param <T> The parent class type
 * @author Mikedeejay2
 */
public class ReflectorField<T>
{
    /**
     * The parent {@link ReflectorClass}
     */
    protected final ReflectorClass<T> clazz;

    /**
     * The reference {@link Field}
     */
    protected final Field field;

    /**
     * Construct a new <code>ReflectorField</code>
     *
     * @param clazz The parent {@link ReflectorClass}
     * @param field The reference {@link Field}
     */
    public ReflectorField(ReflectorClass<T> clazz, Field field)
    {
        this.clazz = clazz;
        this.field = field;
        field.setAccessible(true);
    }

    /**
     * Construct a new <code>ReflectorField</code>
     *
     * @param clazz The parent {@link ReflectorClass}
     * @param name The name of the field
     */
    public ReflectorField(ReflectorClass<T> clazz, String name)
    {
        this.clazz = clazz;
        Field field;
        try
        {
            field = clazz.get().getDeclaredField(name);
            field.setAccessible(true);
        }
        catch(NoSuchFieldException e)
        {
            field = null;
            e.printStackTrace();
        }
        this.field = field;
    }

    /**
     * Get this field's value from an object with a specified type
     *
     * @param obj The object instance, null for static
     * @param type The type to cast the object to
     * @param <R>  The type to cast the object to
     * @return The value of the field from the specified object
     */
    public <R> R get(Object obj, Class<R> type)
    {
        try
        {
            return type.cast(field.get(obj));
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get this field's value from an object
     *
     * @param obj The object instance, null for static
     * @return The value of the field from the specified object
     */
    public Object get(Object obj)
    {
        try
        {
            return field.get(obj);
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the wrapped field for this <code>ReflectorField</code>
     *
     * @return The <code>Field</code>
     */
    public Field get()
    {
        return field;
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
