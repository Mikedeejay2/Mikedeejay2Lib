package com.mikedeejay2.mikedeejay2lib.module;

import com.google.common.collect.ImmutableSet;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionAccessor;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ModuleRegistry implements ModuleRegister<Module>
{
    private final BukkitPlugin plugin;
    private final Set<Module> modules;
    private final Predicate<Module> enablePredicate;

    public ModuleRegistry(@NotNull SectionAccessor<?, ?> config, @Nullable Consumer<Boolean> modified, BukkitPlugin plugin)
    {
        this.plugin = plugin;
        this.modules = new LinkedHashSet<>();

        this.enablePredicate = module -> {
            final String name = module.getName();
            if(!config.contains(name))
            {
                config.setBoolean(name, true);
                if(modified != null)
                {
                    modified.accept(true);
                }
            }
            return config.getBoolean(name);
        };
    }

    @Override
    public <T extends Module> T register(@NotNull T module)
    {
        Validate.notNull(module, "Cannot register null module");
        if(!enablePredicate.test(module))
        {
            plugin.sendInfo(String.format("%s module is disabled in the config. Skipping initialization.", module.getName()));
            return null;
        }
        Validate.isTrue(!module.isEnabled(), String.format("%s module is already enabled but is attempting to be registered", module.getName()));

        T registeredModule = enableModule(module);
        if(registeredModule != null)
        {
            addModule(module);
        }
        return registeredModule;
    }

    @Override
    public void unregister(@NotNull final String name)
    {
        Validate.notNull(name, "Cannot unregister null named module");
        Validate.isTrue(containsModule(name), String.format("Cannot unregister module %s that doesn't exist in the registry", name));

        Module module = getModule(name);
        unregister(module);
    }

    @Override
    public void unregister(@NotNull Class<Module> moduleClass)
    {
        Validate.notNull(moduleClass, "Cannot unregister null class module");
        Validate.isTrue(containsModule(moduleClass), String.format("Cannot unregister module of class %s that doesn't exist in the registry", moduleClass.getName()));

        Module module = getModule(moduleClass);
        unregister(module);
    }

    @Override
    public void unregister(@NotNull Module module)
    {
        Validate.notNull(module, "Cannot unregister null module");
        Validate.isTrue(module.isEnabled(), String.format("%s module is already disabled but is attempting to be unregistered", module.getName()));
        disableModule(module);
        removeModule(module);
    }

    @Override
    public <T extends Module> T enableModule(@NotNull T module)
    {
        plugin.sendInfo(String.format("&eEnabling %s module...", module.getName()));
        try
        {
            Method method = Module.class.getDeclaredMethod("enable");
            method.setAccessible(true);
            method.invoke(module);
        }
        catch(Exception e)
        {
            plugin.sendSevere(String.format("The module %s encountered an exception while enabling", module.getName()));
            e.printStackTrace();
            return null;
        }
        plugin.sendInfo(String.format("&aSuccessfully enabled %s module", module.getName()));
        return module;
    }

    @Override
    public void disableModule(@NotNull Module module)
    {
        plugin.sendInfo(String.format("&eDisabling %s module...", module.getName()));
        try
        {
            Method method = Module.class.getDeclaredMethod("disable");
            method.setAccessible(true);
            method.invoke(module);
        }
        catch(Exception e)
        {
            plugin.sendSevere(String.format("The module %s encountered an exception while disabling", module.getName()));
            e.printStackTrace();
        }
        plugin.sendInfo(String.format("&aSuccessfully disabled %s module", module.getName()));
    }

    @Override
    public <T extends Module> T getModule(@NotNull final String name, @NotNull Class<T> moduleClass)
    {
        Validate.notNull(name, "Cannot get module with a null name");
        Validate.notNull(moduleClass, "Cannot get module with a null class");
        if(!containsModule(name))
        {
            return null;
        }
        Module module = getModule(name);
        if(module.getClass() != moduleClass)
        {
            return null;
        }
        return moduleClass.cast(module);
    }

    @Override
    public Module getModule(@NotNull final String name)
    {
        if(!containsModule(name))
        {
            return null;
        }
        for(Module module : modules)
        {
            if(name.equals(module.getName()))
            {
                return module;
            }
        }
        return null;
    }

    @Override
    public <T extends Module> T getModule(@NotNull Class<T> moduleClass)
    {
        Validate.notNull(moduleClass, "Cannot get module with a null class");
        for(Module module : modules)
        {
            if(module.getClass() == moduleClass)
            {
                return moduleClass.cast(module);
            }
        }
        return null;
    }

    @Override
    public boolean containsModule(@NotNull final String name)
    {
        for(Module module : modules)
        {
            if(name.equals(module.getName())) return true;
        }
        return false;
    }

    @Override
    public boolean containsModule(@NotNull Class<?> moduleClass)
    {
        for(Module module : modules)
        {
            if(module.getClass() == moduleClass)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsModule(@NotNull final String name, @NotNull Class<?> moduleClass) {
        for(Module module : modules)
        {
            if(module.getClass() == moduleClass && name.equals(module.getName()))
            {
                return true;
            }
        }
        return false;
    }

    private <T extends Module> void addModule(@NotNull T module)
    {
        modules.add(module);
    }

    private void removeModule(@NotNull Module module)
    {
        modules.remove(module);
    }

    @Override
    public void unregisterAll() {
        for(Iterator<Module> iterator = modules.iterator(); iterator.hasNext();)
        {
            Module module = iterator.next();
            Validate.notNull(module, "Cannot unregister null module");
            Validate.isTrue(module.isEnabled(), String.format("%s module is already disabled but is attempting to be unregistered", module.getName()));
            disableModule(module);
            iterator.remove();
        }
    }

    @Override
    public Set<Module> getModules()
    {
        return ImmutableSet.copyOf(modules);
    }

    @Override
    public int registrySize()
    {
        return modules.size();
    }
}
