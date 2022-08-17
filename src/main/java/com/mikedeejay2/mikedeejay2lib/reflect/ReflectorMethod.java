package com.mikedeejay2.mikedeejay2lib.reflect;

import java.lang.reflect.Method;

/**
 * A wrapper class for {@link Method} for simpler reflection needs
 *
 * @param <T> The parent class type
 * @author Mikedeejay2
 */
public class ReflectorMethod<T> {
    /**
     * The parent {@link ReflectorClass}
     */
    protected final ReflectorClass<T> clazz;

    /**
     * The reference {@link Method}
     */
    protected final Method method;

    /**
     * Construct a new <code>ReflectorMethod</code>
     *
     * @param clazz  The parent {@link ReflectorClass}
     * @param method The reference {@link Method}
     */
    public ReflectorMethod(ReflectorClass<T> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    /**
     * Construct a new <code>ReflectorMethod</code>
     *
     * @param clazz          The parent {@link ReflectorClass}
     * @param name           The name of the method
     * @param parameterTypes The array of parameter types of the method
     */
    public ReflectorMethod(ReflectorClass<T> clazz, String name, Class<?>... parameterTypes) {
        this.clazz = clazz;
        Method method;
        try {
            method = clazz.get().getDeclaredMethod(name, parameterTypes);
        } catch(NoSuchMethodException e) {
            method = null;
            e.printStackTrace();
        }
        this.method = method;
    }

    /**
     * Invoke the method given specified arguments
     *
     * @param type The return type of the method
     * @param obj  The object to be invoked
     * @param args The arguments to pass to the method for construction. Arguments must match the parameter
     *             types in type and amount of arguments.
     * @param <R>  The return type of the method
     * @return The returned object of the method, if any
     */
    public <R> R invoke(Class<R> type, Object obj, Object... args) {
        try {
            return type.cast(method.invoke(obj, args));
        } catch(ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Invoke the method given specified arguments
     *
     * @param obj  The object to be invoked
     * @param args The arguments to pass to the method for construction. Arguments must match the parameter
     *             types in type and amount of arguments.
     * @return The returned object of the method, if any
     */
    public Object invoke(Object obj, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch(ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the wrapped field for this <code>ReflectorMethod</code>
     *
     * @return The <code>Method</code>
     */
    public Method get() {
        return method;
    }

    /**
     * Get the parent {@link ReflectorClass} of this field
     *
     * @return The parent <code>ReflectorClass</code>
     */
    public ReflectorClass<T> parentClass() {
        return clazz;
    }
}
