package com.mikedeejay2.mikedeejay2lib.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * A wrapper class for {@link Field} for simpler reflection needs
 *
 * @param <T> The parent class type
 * @author Mikedeejay2
 */
public class ReflectorField<T> {
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
    public ReflectorField(ReflectorClass<T> clazz, Field field) {
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
    public ReflectorField(ReflectorClass<T> clazz, String name) {
        this.clazz = clazz;
        Field field;
        try {
            field = clazz.get().getDeclaredField(name);
            field.setAccessible(true);
        } catch(NoSuchFieldException e) {
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
    public <R> R get(Object obj, Class<R> type) {
        try {
            return type.cast(field.get(obj));
        } catch(IllegalAccessException e) {
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
    public Object get(Object obj) {
        try {
            return field.get(obj);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Set this field's value to an object
     *
     * @param obj The object instance, null for static
     * @param value The value of the field to set
     * @return This object
     */
    public ReflectorField<T> set(Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Set this field's value to a boolean
     *
     * @param obj The object instance, null for static
     * @param value The value of the field to set
     * @return This object
     */
    public ReflectorField<T> set(Object obj, boolean value) {
        try {
            field.setBoolean(obj, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Set this field's value to a byte
     *
     * @param obj The object instance, null for static
     * @param value The value of the field to set
     * @return This object
     */
    public ReflectorField<T> set(Object obj, byte value) {
        try {
            field.setByte(obj, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Set this field's value to a char
     *
     * @param obj The object instance, null for static
     * @param value The value of the field to set
     * @return This object
     */
    public ReflectorField<T> set(Object obj, char value) {
        try {
            field.setChar(obj, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Set this field's value to a short
     *
     * @param obj The object instance, null for static
     * @param value The value of the field to set
     * @return This object
     */
    public ReflectorField<T> set(Object obj, short value) {
        try {
            field.setShort(obj, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Set this field's value to an int
     *
     * @param obj The object instance, null for static
     * @param value The value of the field to set
     * @return This object
     */
    public ReflectorField<T> set(Object obj, int value) {
        try {
            field.setInt(obj, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Set this field's value to a float
     *
     * @param obj The object instance, null for static
     * @param value The value of the field to set
     * @return This object
     */
    public ReflectorField<T> set(Object obj, float value) {
        try {
            field.setFloat(obj, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Set this field's value to a double
     *
     * @param obj The object instance, null for static
     * @param value The value of the field to set
     * @return This object
     */
    public ReflectorField<T> set(Object obj, double value) {
        try {
            field.setDouble(obj, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Set this field's value to a long
     *
     * @param obj The object instance, null for static
     * @param value The value of the field to set
     * @return This object
     */
    public ReflectorField<T> set(Object obj, long value) {
        try {
            field.setLong(obj, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Get the wrapped field for this <code>ReflectorField</code>
     *
     * @return The <code>Field</code>
     */
    public Field get() {
        return field;
    }

    /**
     * Get the parent {@link ReflectorClass} of this field
     *
     * @return The parent <code>ReflectorClass</code>
     */
    public ReflectorClass<T> parentClass() {
        return clazz;
    }

    /**
     * Remove a modifier from this <code>ReflectorField</code>
     *
     * @param modifier See {@link Modifier}, for example <code>Modifier.FINAL</code>
     *
     * @return This object
     */
    public ReflectorField<T> removeModifier(int modifier) {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~modifier);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a modifier to this <code>ReflectorField</code>
     *
     * @param modifier See {@link Modifier}, for example <code>Modifier.FINAL</code>
     *
     * @return This object
     */
    public ReflectorField<T> addModifier(int modifier) {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & modifier);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }
}
