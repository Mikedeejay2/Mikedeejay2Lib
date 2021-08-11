package com.mikedeejay2.mikedeejay2lib.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * A wrapper class for {@link Class} for simpler reflection needs
 *
 * @param <T> The class type
 * @author Mikedeejay2
 */
public class ReflectorClass<T>
{
    /**
     * The reference {@link Class}
     */
    protected final Class<T> clazz;

    /**
     * Construct a new <code>ReflectorClass</code>
     *
     * @param clazz The reference {@link Class}
     */
    public ReflectorClass(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * Get the wrapped {@link Class}
     *
     * @return The class reference
     */
    public Class<T> get()
    {
        return clazz;
    }

    /**
     * Get a {@link ReflectorConstructor} based off of the constructor's parameter types
     *
     * @param parameterTypes The array of parameter types of the constructor
     * @return A new {@link ReflectorConstructor}
     */
    public ReflectorConstructor<T> constructor(Class<?>... parameterTypes)
    {
        return new ReflectorConstructor<>(this, parameterTypes);
    }

    /**
     * Get a {@link ReflectorConstructor} based off of an existing {@link Constructor}
     *
     * @param constructor The reference <code>Constructor</code>
     * @return A new {@link ReflectorConstructor}
     */
    public ReflectorConstructor<T> constructor(Constructor<T> constructor)
    {
        return new ReflectorConstructor<>(this, constructor);
    }

    /**
     * Get a {@link ReflectorField} based off of the field's name in the class.
     *
     * @param name The name of the field to get
     * @return A new {@link ReflectorField}
     */
    public ReflectorField<T> field(String name)
    {
        return new ReflectorField<>(this, name);
    }

    /**
     * Get a {@link ReflectorField} based off of an existing {@link Field}
     *
     * @param field The reference <code>Field</code>
     * @return A new {@link ReflectorField}
     */
    public ReflectorField<T> field(Field field)
    {
        return new ReflectorField<>(this, field);
    }

    /**
     * Get a {@link ReflectorMethod} based off of the method's name in the class.
     *
     * @param name The name of the method to get
     * @param parameterTypes The list of parameter types of the method
     * @return A new {@link ReflectorMethod}
     */
    public ReflectorMethod<T> method(String name, Class<?>... parameterTypes)
    {
        return new ReflectorMethod<>(this, name, parameterTypes);
    }

    /**
     * Get a {@link ReflectorMethod} based off of an existing {@link Method}
     *
     * @param method The reference <code>Method</code>
     * @return A new {@link ReflectorMethod}
     */
    public ReflectorMethod<T> method(Method method)
    {
        return new ReflectorMethod<>(this, method);
    }
}
