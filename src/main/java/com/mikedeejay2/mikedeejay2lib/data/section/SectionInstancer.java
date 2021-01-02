package com.mikedeejay2.mikedeejay2lib.data.section;

/**
 * Interface for instancing a SectionAccessor, usually used in a <tt>DataFile</tt>
 *
 * @param <T> The type of <tt>SectionAccessor</tt> that should be used
 *
 * @author Mikedeejay2
 */
public interface SectionInstancer<T extends SectionAccessor>
{
    public T getAccessor(String name);
    public T getAccessor();
}
