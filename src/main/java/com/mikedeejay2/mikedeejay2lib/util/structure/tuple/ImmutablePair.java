package com.mikedeejay2.mikedeejay2lib.util.structure.tuple;

/**
 * Holds two immutable data values.
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
public class ImmutablePair<L, R> extends Pair<L, R> {
    /**
     * Internal serial version unique ID
     */
    private static final long serialVersionUID = -1270737821267829565L;

    /**
     * The left value
     */
    protected final L left;

    /**
     * The right value
     */
    protected final R right;

    public ImmutablePair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Get the left value
     *
     * @return The left value
     */
    @Override
    public L getLeft() {
        return left;
    }

    /**
     * Get the right value
     *
     * @return The right value
     */
    @Override
    public R getRight() {
        return right;
    }

    /**
     * Get the key (left)
     *
     * @return The key
     */
    @Override
    public L getKey() {
        return left;
    }

    /**
     * Get the value (right)
     *
     * @return The value
     */
    @Override
    public R getValue() {
        return right;
    }

    /**
     * Unsupported for immutable data types, do not use
     *
     * @param value The new value
     * @return Nothing, exception is always thrown
     * @throws UnsupportedOperationException This operation is not supported for an immutable data type
     */
    @Override
    @Deprecated
    public R setValue(R value) {
        throw new UnsupportedOperationException();
    }
}
