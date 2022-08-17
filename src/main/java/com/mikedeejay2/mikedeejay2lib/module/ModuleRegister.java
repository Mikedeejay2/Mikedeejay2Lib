package com.mikedeejay2.mikedeejay2lib.module;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * A module registry class interface
 *
 * @param <T> The module data type
 * @see ModuleRegistry
 *
 * @author Mikedeejay2
 */
public interface ModuleRegister<T extends ModuleInterface> {
    /**
     * Register a new {@link Module} to the registry
     *
     * @param module The module to register
     * @param <V>    The module's data type
     * @return The registered module, null if not registered
     */
    <V extends T> V register(@NotNull V module);

    /**
     * Unregister a <code>ModuleInterface</code> from the registry by the name of the module
     * <p>
     * The module must be registered and enabled to be unregistered.
     *
     * @param name The name of module to unregister
     */
    void unregister(@NotNull final String name);

    /**
     * Unregister a <code>ModuleInterface</code> based off of the module's <code>Class</code>.
     * <p>
     * It must be known that a module of that class exists in the registry, an exception will be thrown if not.
     * <p>
     * The module must be registered and enabled to be unregistered.
     *
     * @param moduleClass The class of the module to be unregistered
     */
    void unregister(@NotNull Class<T> moduleClass);

    /**
     * Unregister a <code>ModuleInterface</code> based off of the module's instance.
     * <p>
     * The module must be registered and enabled to be unregistered.
     *
     * @param module The module instance ot remove
     */
    void unregister(@NotNull T module);

    /**
     * Enable a <code>ModuleInterface</code>.
     * <p>
     * {@link ModuleRegistry#register(Module)} already does this
     *
     * @param module The module to enable
     * @param <V>    The data type of the module
     * @return The enabled module, null if enable failed
     */
    <V extends T> V enableModule(@NotNull V module);

    /**
     * Disable a <code>ModuleInterface</code>.
     * <p>
     * The module must be registered and enabled to be disabled.
     *
     * @param module The module to disable
     */
    void disableModule(@NotNull T module);

    /**
     * Get a <code>ModuleInterface</code> based off of the module's name and class type
     *
     * @param name        The name of the module to get
     * @param moduleClass The module's <code>Class</code> to find
     * @param <V>         The data type of the module
     * @return The requested module
     */
    <V extends T> V getModule(@NotNull final String name, @NotNull Class<V> moduleClass);

    /**
     * Get a <code>ModuleInterface</code> based off of the module's name
     *
     * @param name The name of the module to get
     * @return The requested module
     */
    T getModule(@NotNull final String name);

    /**
     * Get a <code>ModuleInterface</code> based off of the module's class
     *
     * @param moduleClass The <code>Class</code> of the module to get
     * @param <V>         The data type of the module
     * @return The requested module
     */
    <V extends T> V getModule(@NotNull Class<V> moduleClass);

    /**
     * Get whether this registry contains a <code>ModuleInterface</code> with a specified name
     *
     * @param name The name to attempt to find
     * @return Whether this registry contains a module of the specified name or not
     */
    boolean containsModule(@NotNull final String name);

    /**
     * Get whether this registry contains a <code>ModuleInterface</code> with a specified class
     *
     * @param moduleClass The <code>Class</code> of the module to find
     * @return Whether this registry contains a module of the specified class or not
     */
    boolean containsModule(@NotNull Class<?> moduleClass);

    /**
     * Get whether this registry contains a <code>ModuleInterface</code> with a specified name and class
     *
     * @param name        The name to attempt to find
     * @param moduleClass The <code>Class</code> of the module to find
     * @return Whether this registry contains a module of the specified name and class or not
     */
    boolean containsModule(@NotNull final String name, @NotNull Class<?> moduleClass);

    /**
     * Unregister all <code>ModuleInterfaces</code> from this registry.
     * as well.
     */
    void unregisterAll();

    /**
     * Get a set of all of the <code>ModuleInterfaces</code> in this registry
     *
     * @return A set of all modules
     */
    Set<T> getModules();

    /**
     * Get the current size of the modules set in this registry
     *
     * @return The registry size
     */
    int registrySize();
}
