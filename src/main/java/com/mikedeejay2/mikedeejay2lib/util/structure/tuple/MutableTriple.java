package com.mikedeejay2.mikedeejay2lib.util.structure.tuple;

/**
 * Holds three mutable data values.
 * <p>
 * Apache-independent implementation of a Triple data structure. Based off of Apache Commons Triple, this Triple
 * is created because Apache Commons 3 is not available without the use of the spigot jar, therefore this class
 * was created for use of the data structure.
 *
 * @param <L> The left data type
 * @param <M> The middle data type
 * @param <R> The right data type
 *
 * @author Mikedeejay2
 */
public class MutableTriple<L, M, R> extends Triple<L, M, R> {
    /**
     * Internal serial version unique ID
     */
    private static final long serialVersionUID = 3475758183004145568L;

    /**
     * The left value
     */
    protected L left;

    /**
     * The middle value
     */
    protected M middle;

    /**
     * The right value
     */
    protected R right;

    /**
     * Construct a new <code>MutableTriple</code>
     *
     * @param left The left value
     * @param middle The middle value
     * @param right The right value
     */
    public MutableTriple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
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
     * Get the middle value
     *
     * @return The middle value
     */
    @Override
    public M getMiddle() {
        return middle;
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
     * Set the left value
     *
     * @param left The new left value
     * @return The new left value
     */
    public L setLeft(L left) {
        this.left = left;
        return left;
    }

    /**
     * Set the middle value
     *
     * @param middle The new middle value
     * @return The new middle value
     */
    public M setMiddle(M middle) {
        this.middle = middle;
        return middle;
    }


    /**
     * Set the right value
     *
     * @param right The new right value
     * @return The new right value
     */
    public R setRight(R right) {
        this.right = right;
        return right;
    }
}
