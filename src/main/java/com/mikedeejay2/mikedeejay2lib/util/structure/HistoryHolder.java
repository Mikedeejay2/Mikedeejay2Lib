package com.mikedeejay2.mikedeejay2lib.util.structure;

import java.util.Stack;

/**
 * Holds two stacks, a forward and backward stack with helper methods of getting objects.
 * <p>
 * Based off of a First In First Out basis like web browser history or similar.
 *
 * @param <T> The data being stored
 * @author Mikedeejay2
 */
public class HistoryHolder<T> {
    /**
     * The back history stack
     */
    protected Stack<T> back;

    /**
     * The forward history stack
     */
    protected Stack<T> forward;

    /**
     * Construct a new <code>HistoryHolder</code>
     */
    public HistoryHolder() {
        this.back = new Stack<>();
        this.forward = new Stack<>();
    }

    /**
     * Returns whether back data exists
     *
     * @return If this system has back data
     */
    public boolean hasBack() {
        return !back.empty();
    }

    /**
     * Returns whether forward data exists
     *
     * @return If this system has forward data
     */
    public boolean hasForward() {
        return !forward.empty();
    }

    /**
     * Get the size of the back stack
     *
     * @return The back stack size
     */
    public int backSize() {
        return back.size();
    }

    /**
     * Get the size of the forward stack
     *
     * @return The forward stack size
     */
    public int forwardSize() {
        return forward.size();
    }

    /**
     * Clear the back data stack
     */
    public void clearBack() {
        back.clear();
    }

    /**
     * Clear the forward data stack
     */
    public void clearForward() {
        forward.clear();
    }

    /**
     * Pops the previous back data
     *
     * @return The previous back data
     */
    public T popBack() {
        return back.pop();
    }

    /**
     * Pops the previous forward data
     *
     * @return The previous forward data
     */
    public T popForward() {
        return forward.pop();
    }

    /**
     * Peek the previous back data
     *
     * @return The previous back data
     */
    public T peekBack() {
        return back.peek();
    }

    /**
     * Peek the previous forward data
     *
     * @return The previous forward data
     */
    public T peekForward() {
        return forward.peek();
    }

    /**
     * Get the back stack of data
     *
     * @return The back stack of data
     */
    public Stack<T> getBackStack() {
        return back;
    }

    /**
     * Get the forward stack of data
     *
     * @return The forward stack of data
     */
    public Stack<T> getForwardStack() {
        return forward;
    }

    /**
     * Push data to the back stack
     *
     * @param obj The data to add to the back
     */
    public void pushBack(T obj) {
        if(hasBack() && peekBack() == obj) return;
        back.push(obj);
    }

    /**
     * Push data to the forward stack
     *
     * @param data The data to add to the forward
     */
    public void pushForward(T data) {
        if(hasForward() && peekForward() == data) return;
        forward.push(data);
    }
}
