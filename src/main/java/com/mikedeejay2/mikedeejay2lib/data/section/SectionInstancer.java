package com.mikedeejay2.mikedeejay2lib.data.section;

/**
 * Interface for instancing a SectionAccessor, usually used in a <code>DataFile</code>
 *
 * @param <T> The type of <code>SectionAccessor</code> that should be used
 *
 * @author Mikedeejay2
 */
public interface SectionInstancer<T extends SectionAccessor>
{
    T getAccessor(String name);
    T getAccessor();
}
