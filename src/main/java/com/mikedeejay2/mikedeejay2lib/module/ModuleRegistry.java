package com.mikedeejay2.mikedeejay2lib.module;

import com.google.common.collect.ImmutableSet;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Registry class for {@link Module}s and other module related systems.
 * <p>
 * To get started, see {@link ModuleRegistry#register(Module)}
 *
 * @author Mikedeejay2
 */
public class ModuleRegistry implements ModuleRegister<Module> {
    /**
     * The {@link BukkitPlugin} instance
     */
    private final BukkitPlugin plugin;

    /**
     * The set of active modules
     */
    private final Set<Module> modules;
    /**
     * The enable predicate
     */
    private final Predicate<Module> enablePredicate;

    /**
     * Construct a new module registry
     *
     * @param enablePredicate The predicate checked before enabling a module. Could be linked
     *                        to a config file or similar to only enable a module if the module
     *                        was found in a list or something similar.
     * @param plugin          A reference to the plugin
     */
    public ModuleRegistry(@Nullable Predicate<Module> enablePredicate, BukkitPlugin plugin) {
        this.plugin = plugin;
        this.modules = new LinkedHashSet<>();

        if(enablePredicate == null) {
            enablePredicate = (module) -> true;
        }
        this.enablePredicate = enablePredicate;
    }

    /**
     * Register a new {@link Module} to the registry
     *
     * @param module The module to register
     * @param <T>    The module's data type
     * @return The registered module, null if not registered
     */
    @Override
    public <T extends Module> T register(@NotNull T module) {
        Validate.notNull(module, "Cannot register null module");
        if(!enablePredicate.test(module)) {
            plugin.sendInfo(String.format("%s module is disabled in the config. Skipping initialization.", module.getName()));
            return null;
        }
        Validate.isTrue(!module.isEnabled(), String.format("%s module is already enabled but is attempting to be registered", module.getName()));

        T registeredModule = enableModule(module);
        if(registeredModule != null) {
            addModule(module);
        }
        return registeredModule;
    }

    /**
     * Unregister a <code>Module</code> from the registry by the name of the module
     * <p>
     * The module must be registered and enabled to be unregistered.
     *
     * @param name The name of module to unregister
     */
    @Override
    public void unregister(@NotNull final String name) {
        Validate.notNull(name, "Cannot unregister null named module");
        Validate.isTrue(containsModule(name), String.format("Cannot unregister module %s that doesn't exist in the registry", name));

        Module module = getModule(name);
        unregister(module);
    }

    /**
     * Unregister a <code>Module</code> based off of the module's <code>Class</code>.
     * <p>
     * It must be known that a module of that class exists in the registry, an exception will be thrown if not.
     * <p>
     * The module must be registered and enabled to be unregistered.
     *
     * @param moduleClass The class of the module to be unregistered
     */
    @Override
    public void unregister(@NotNull Class<Module> moduleClass) {
        Validate.notNull(moduleClass, "Cannot unregister null class module");
        Validate.isTrue(containsModule(moduleClass), String.format("Cannot unregister module of class %s that doesn't exist in the registry", moduleClass.getName()));

        Module module = getModule(moduleClass);
        unregister(module);
    }

    /**
     * Unregister a <code>Module</code> based off of the module's instance.
     * <p>
     * The module must be registered and enabled to be unregistered.
     *
     * @param module The module instance ot remove
     */
    @Override
    public void unregister(@NotNull Module module) {
        Validate.notNull(module, "Cannot unregister null module");
        Validate.isTrue(module.isEnabled(), String.format("%s module is already disabled but is attempting to be unregistered", module.getName()));
        disableModule(module);
        removeModule(module);
    }

    /**
     * Enable a <code>Module</code>. {@link ModuleRegistry#register(Module)} already does this.
     * <p>
     * {@link ModuleRegistry#register(Module)} already does this
     *
     * @param module The module to enable
     * @param <T>    The data type of the module
     * @return The enabled module, null if enable failed
     */
    @Override
    public <T extends Module> T enableModule(@NotNull T module) {
        plugin.sendInfo(String.format("&eEnabling %s module...", module.getName()));
        try {
            Method method = Module.class.getDeclaredMethod("enable");
            method.setAccessible(true);
            method.invoke(module);
        } catch(Exception e) {
            plugin.sendSevere(String.format("The module %s encountered an exception while enabling", module.getName()));
            e.printStackTrace();
            return null;
        }
        plugin.sendInfo(String.format("&aSuccessfully enabled %s module", module.getName()));
        return module;
    }

    /**
     * Disable a <code>Module</code>.
     * <p>
     * The module must be registered and enabled to be disabled.
     * <p>
     * {@link ModuleRegistry#unregister(Module)} already does this
     *
     * @param module The module to disable
     */
    @Override
    public void disableModule(@NotNull Module module) {
        plugin.sendInfo(String.format("&eDisabling %s module...", module.getName()));
        try {
            Method method = Module.class.getDeclaredMethod("disable");
            method.setAccessible(true);
            method.invoke(module);
        } catch(Exception e) {
            plugin.sendSevere(String.format("The module %s encountered an exception while disabling", module.getName()));
            e.printStackTrace();
        }
        plugin.sendInfo(String.format("&aSuccessfully disabled %s module", module.getName()));
    }

    /**
     * Get a <code>Module</code> based off of the module's name and class type
     *
     * @param name        The name of the module to get
     * @param moduleClass The module's <code>Class</code> to find
     * @param <T>         The data type of the module
     * @return The requested module
     */
    @Override
    public <T extends Module> T getModule(@NotNull final String name, @NotNull Class<T> moduleClass) {
        Validate.notNull(name, "Cannot get module with a null name");
        Validate.notNull(moduleClass, "Cannot get module with a null class");
        if(!containsModule(name)) {
            return null;
        }
        Module module = getModule(name);
        if(module.getClass() != moduleClass) {
            return null;
        }
        return moduleClass.cast(module);
    }

    /**
     * Get a <code>Module</code> based off of the module's name
     *
     * @param name The name of the module to get
     * @return The requested module
     */
    @Override
    public Module getModule(@NotNull final String name) {
        if(!containsModule(name)) {
            return null;
        }
        for(Module module : modules) {
            if(name.equals(module.getName())) {
                return module;
            }
        }
        return null;
    }

    /**
     * Get a <code>Module</code> based off of the module's class
     *
     * @param moduleClass The <code>Class</code> of the module to get
     * @param <T>         The data type of the module
     * @return The requested module
     */
    @Override
    public <T extends Module> T getModule(@NotNull Class<T> moduleClass) {
        Validate.notNull(moduleClass, "Cannot get module with a null class");
        for(Module module : modules) {
            if(module.getClass() == moduleClass) {
                return moduleClass.cast(module);
            }
        }
        return null;
    }

    /**
     * Get whether this registry contains a <code>Module</code> with a specified name
     *
     * @param name The name to attempt to find
     * @return Whether this registry contains a module of the specified name or not
     */
    @Override
    public boolean containsModule(@NotNull final String name) {
        for(Module module : modules) {
            if(name.equals(module.getName())) return true;
        }
        return false;
    }

    /**
     * Get whether this registry contains a <code>Module</code> with a specified class
     *
     * @param moduleClass The <code>Class</code> of the module to find
     * @return Whether this registry contains a module of the specified class or not
     */
    @Override
    public boolean containsModule(@NotNull Class<?> moduleClass) {
        for(Module module : modules) {
            if(module.getClass() == moduleClass) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get whether this registry contains a <code>Module</code> with a specified name and class
     *
     * @param name        The name to attempt to find
     * @param moduleClass The <code>Class</code> of the module to find
     * @return Whether this registry contains a module of the specified name and class or not
     */
    @Override
    public boolean containsModule(@NotNull final String name, @NotNull Class<?> moduleClass) {
        for(Module module : modules) {
            if(module.getClass() == moduleClass && name.equals(module.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a <code>Module</code> to the modules Set
     *
     * @param module The module to add
     * @param <T>    The data type of the module
     */
    private <T extends Module> void addModule(@NotNull T module) {
        modules.add(module);
    }

    /**
     * Remove a <code>Module</code> from the modules Set
     *
     * @param module The module to remove
     */
    private void removeModule(@NotNull Module module) {
        modules.remove(module);
    }

    /**
     * Unregister all <code>Modules</code> from this registry. This calls {@link Module#onDisable()} for all modules
     * as well.
     */
    @Override
    public void unregisterAll() {
        for(Iterator<Module> iterator = modules.iterator(); iterator.hasNext();) {
            Module module = iterator.next();
            Validate.notNull(module, "Cannot unregister null module");
            Validate.isTrue(module.isEnabled(), String.format("%s module is already disabled but is attempting to be unregistered", module.getName()));
            disableModule(module);
            iterator.remove();
        }
    }

    /**
     * Get an {@link ImmutableSet} of all of the <code>Modules</code> in this registry
     *
     * @return A set of all modules
     */
    @Override
    public Set<Module> getModules() {
        return ImmutableSet.copyOf(modules);
    }

    /**
     * Get the current size of the modules set in this registry
     *
     * @return The registry size
     */
    @Override
    public int registrySize() {
        return modules.size();
    }
}
