package com.mikedeejay2.mikedeejay2lib.util.structure.tuple;

import java.io.Serializable;

/**
 * Holds three data values.
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
public abstract class Triple<L, M, R> implements Comparable<Triple<L, M, R>>, Serializable
{
    /**
     * Internal serial version unique ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Get the left value
     *
     * @return The left value
     */
    public abstract L getLeft();

    /**
     * Get the middle value
     *
     * @return The middle value
     */
    public abstract M getMiddle();

    /**
     * Get the right value
     *
     * @return The right value
     */
    public abstract R getRight();

    /**
     * Compare one triple to another
     *
     * @param other The other <code>Triple</code> to compare
     * @return The comparison result
     */
    @Override
    public int compareTo(Triple<L, M, R> other)
    {
        if(this == other) return 0;
        if(this.getLeft() == other.getLeft() &&
                this.getMiddle() == other.getMiddle() &&
                this.getRight() == other.getRight()) return 0;
        if(this.getLeft() == null || this.getMiddle() == null || this.getRight() == null) return -1;
        if(other.getLeft() == null || other.getMiddle() == null || other.getRight() == null) return 1;
        if(this.getLeft().equals(other.getLeft()) &&
                this.getMiddle().equals(other.getMiddle()) &&
                this.getRight().equals(other.getRight())) return 0;
        int comparison;
        comparison = ((Comparable) this.getLeft()).compareTo(other.getLeft());
        comparison = comparison == 0 ? ((Comparable) this.getMiddle()).compareTo(other.getMiddle()) : comparison;
        comparison = comparison == 0 ? ((Comparable) this.getRight()).compareTo(other.getRight()) : comparison;
        return comparison;
    }

    /**
     * Get whether this triple equals another object
     *
     * @param obj The other object
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if(!(obj instanceof Triple<?, ?, ?>)) return false;
        Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
        if((this.getLeft() == null && other.getLeft() != null) || (this.getLeft() != null && other.getLeft() == null))
        {
            return false;
        }
        if((this.getMiddle() == null && other.getMiddle() != null) || (this.getMiddle() != null && other.getMiddle() == null))
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
        if(this.getMiddle() != null && !this.getMiddle().equals(other.getMiddle()))
        {
            return false;
        }
        if(this.getRight() != null && !this.getRight().equals(other.getRight()))
        {
            return false;
        }
        return true;
    }

    /**
     * To String method
     *
     * @return The generated String
     */
    @Override
    public String toString()
    {
        return "(" + getLeft() + ", " + getMiddle() + ", " + getRight() + ")";
    }
}
