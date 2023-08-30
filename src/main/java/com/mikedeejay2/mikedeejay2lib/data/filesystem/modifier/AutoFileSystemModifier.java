package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.google.common.collect.ImmutableList;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;

public class AutoFileSystemModifier<T extends ConfigurationSerializable> implements FileSystemModifier<T> {
    protected final SerializableFileSystem<T> system;

    public AutoFileSystemModifier(SerializableFileSystem<T> system) {
        this.system = system;
    }

    @Override
    public void addItem(SerializableFolderFS<T> owner, String name, T item) {
        owner.getItemsRaw().put(name, item);
        system.getSaveLoad().saveItem(owner, name, item);
    }

    @Override
    public void removeItem(SerializableFolderFS<T> owner, String name) {
        owner.getItemsRaw().remove(name);
        system.getSaveLoad().deleteItem(owner.getPath(), name);
    }

    @Override
    public void clearItems(SerializableFolderFS<T> owner) {
        final List<String> names = ImmutableList.copyOf(owner.getItemsRaw().keySet());
        owner.getItemsRaw().clear();
        system.getSaveLoad().startCommit();
        for(String name : names) {
            system.getSaveLoad().deleteItem(owner.getPath(), name);
        }
        system.getSaveLoad().commit();
    }

    @Override
    public void addFolder(SerializableFolderFS<T> owner, String name, SerializableFolderFS<T> folder) {
        owner.getFoldersRaw().put(name, folder);
        system.getSaveLoad().saveFolder(folder);
    }

    @Override
    public void removeFolder(SerializableFolderFS<T> owner, String name) {
        owner.getFoldersRaw().remove(name);
        system.getSaveLoad().deleteFolder(SerializableFileSystem.getPath(owner.getPath(), name));
    }

    @Override
    public void clearFolders(SerializableFolderFS<T> owner) {
        final List<String> names = ImmutableList.copyOf(owner.getFoldersRaw().keySet());
        owner.getFoldersRaw().clear();
        system.getSaveLoad().startCommit();
        final String path = owner.getPath();
        for(String name : names) {
            system.getSaveLoad().deleteFolder(path == null ? name : path + "/" + name);
        }
        system.getSaveLoad().commit();
    }

    @Override
    public void save(SerializableFolderFS<T> owner) {}

    @Override
    public void saveAll() {}
}
