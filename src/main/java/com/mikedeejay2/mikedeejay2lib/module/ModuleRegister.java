package com.mikedeejay2.mikedeejay2lib.module;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ModuleRegister<T extends ModuleInterface>
{
    <V extends T> V register(@NotNull V module);

    void unregister(@NotNull final String name);

    void unregister(@NotNull Class<T> moduleClass);

    void unregister(@NotNull T module);

    <V extends T> V enableModule(@NotNull V module);

    void disableModule(@NotNull T module);

    <V extends T> V getModule(@NotNull final String name, @NotNull Class<V> moduleClass);

    T getModule(@NotNull final String name);

    <V extends T> V getModule(@NotNull Class<V> moduleClass);

    boolean containsModule(@NotNull final String name);

    boolean containsModule(@NotNull Class<?> moduleClass);

    boolean containsModule(@NotNull final String name, @NotNull Class<?> moduleClass);

    void unregisterAll();

    Set<T> getModules();

    int registrySize();
}
