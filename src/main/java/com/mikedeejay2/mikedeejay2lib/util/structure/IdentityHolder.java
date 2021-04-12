package com.mikedeejay2.mikedeejay2lib.util.structure;

/**
 * Holds an ID for an implementing class
 *
 * @param <T> The data type of the identity
 *
 * @author Mikedeejay2
 */
public interface IdentityHolder<T>
{
    /**
     * Get the ID of the owning object
     *
     * @return The ID
     */
    T getID();
}
