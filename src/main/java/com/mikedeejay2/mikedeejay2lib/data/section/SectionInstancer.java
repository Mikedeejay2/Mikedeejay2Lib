package com.mikedeejay2.mikedeejay2lib.data.section;

import com.mikedeejay2.mikedeejay2lib.data.DataFile;

/**
 * Interface for instancing a SectionAccessor, usually used in a <code>DataFile</code>
 *
 * @param <R> The type of <code>SectionAccessor</code> that should be used
 * @param <D> The DataFile type that is being accessed.
 * @param <T> The default return type of the Section Accessor, see <code>get</code> and <code>set</code> methods
 *
 * @author Mikedeejay2
 */
public interface SectionInstancer<R extends SectionAccessor<D, T>, D extends DataFile, T>
{
    R getAccessor(String name);
    R getAccessor();
}
