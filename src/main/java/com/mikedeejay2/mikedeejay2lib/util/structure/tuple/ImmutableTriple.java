package com.mikedeejay2.mikedeejay2lib.util.structure.tuple;

/**
 * Holds three immutable data values.
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
public class ImmutableTriple<L, M, R> extends Triple<L, M, R>
{
    private static final long serialVersionUID = -702456999580241256L;

    protected final L left;
    protected final M middle;
    protected final R right;

    /**
     * Construct a new <code>ImmutableTriple</code>
     *
     * @param left The left value
     * @param middle The middle value
     * @param right The right value
     */
    public ImmutableTriple(L left, M middle, R right)
    {
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
    public L getLeft()
    {
        return left;
    }

    /**
     * Get the middle value
     *
     * @return The middle value
     */
    @Override
    public M getMiddle()
    {
        return middle;
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
}
