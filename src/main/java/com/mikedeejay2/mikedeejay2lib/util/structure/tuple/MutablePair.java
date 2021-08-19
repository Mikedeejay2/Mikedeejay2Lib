package com.mikedeejay2.mikedeejay2lib.util.structure.tuple;

/**
 * Holds two mutable data values.
 * <p>
 * Apache-independent implementation of a Pair data structure. Based off of Apache Commons Pair, this Pair
 * is created because Apache Commons 3 is not available without the use of the spigot jar, therefore this class
 * was created for use of the data structure.
 *
 * @param <L> The left data type
 * @param <R> The right data type
 *
 * @author Mikedeejay2
 */
public class MutablePair<L, R> extends Pair<L, R>
{
    /**
     * Internal serial version unique ID
     */
    private static final long serialVersionUID = -1612410683102542176L;

    /**
     * The left value
     */
    protected L left;

    /**
     * The right value
     */
    protected R right;

    public MutablePair(L left, R right)
    {
        this.left = left;
        this.right = right;
    }

    /**
     * Get the left value
     *
     * @return The left value
     */
    @Override
    public L getLeft()
    {
        return left;
    }

    /**
     * Get the right value
     *
     * @return The right value
     */
    @Override
    public R getRight()
    {
        return right;
    }

    /**
     * Get the key (left)
     *
     * @return The key
     */
    @Override
    public L getKey()
    {
        return left;
    }

    /**
     * Get the value (right)
     *
     * @return The value
     */
    @Override
    public R getValue()
    {
        return right;
    }

    /**
     * Set the value (right)
     *
     * @param value The new value
     * @return The new value
     */
    @Override
    public R setValue(R value)
    {
        this.right = value;
        return right;
    }

    /**
     * Set the left value
     *
     * @param left The new left value
     * @return The new left value
     */
    public L setLeft(L left)
    {
        this.left = left;
        return left;
    }

    /**
     * Set the right value
     *
     * @param right The new right value
     * @return The new right value
     */
    public R setRight(R right)
    {
        this.right = right;
        return right;
    }
}
