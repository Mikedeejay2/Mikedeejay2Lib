package com.mikedeejay2.mikedeejay2lib.file.section;

public interface SectionInstancer<T extends SectionAccessor>
{
    public T getAccessor(String name);
    public T getRootAccessor();
}
