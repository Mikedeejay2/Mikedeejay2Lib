package com.mikedeejay2.mikedeejay2lib.util.structure.tuple;

import java.io.Serializable;
import java.util.Map;

/**
 * Holds two data values.
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
public abstract class Pair<L, R> implements Map.Entry<L, R>, Comparable<Pair<L, R>>, Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Get the left value
     *
     * @return The left value
     */
    public abstract L getLeft();

    /**
     * Get the right value
     *
     * @return The right value
     */
    public abstract R getRight();

    @Override
    public int compareTo(Pair<L, R> other)
    {
        if(this == other) return 0;
        if(this.getLeft() == other.getLeft() &&
                this.getRight() == other.getRight()) return 0;
        if(this.getLeft() == null || this.getRight() == null) return -1;
        if(other.getLeft() == null || other.getRight() == null) return 1;
        if(this.getLeft().equals(other.getLeft()) &&
                this.getRight().equals(other.getRight())) return 0;
        int comparison;
        comparison = ((Comparable) this.getLeft()).compareTo(other.getLeft());
        comparison = comparison == 0 ? ((Comparable) this.getRight()).compareTo(other.getRight()) : comparison;
        return comparison;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if(!(obj instanceof Pair<?, ?>)) return false;
        Pair<?, ?> other = (Pair<?, ?>) obj;
        if((this.getLeft() == null && other.getLeft() != null) || (this.getLeft() != null && other.getLeft() == null))
        {
            return false;
        }
        if((this.getRight() == null && other.getRight() != null) || (this.getRight() != null && other.getRight() == null))
        {
            return false;
        }
        if(this.getLeft() != null && !this.getLeft().equals(other.getLeft()))
        {
            return false;
        }
        if(this.getRight() != null && !this.getRight().equals(other.getRight()))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "(" + getLeft() + ", " + getRight() + ")";
    }
}
