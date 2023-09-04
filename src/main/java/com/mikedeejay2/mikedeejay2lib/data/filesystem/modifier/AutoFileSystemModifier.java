package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.google.common.collect.ImmutableList;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;

public class AutoFileSystemModifier<T extends ConfigurationSerializable> extends BaseSystemModifier<T> {
    public AutoFileSystemModifier(SerializableFileSystem<T> system) {
        super(system);
    }

    @Override
    public void addItem(SerializableFolderFS<T> owner, String name, T item) {
        super.addItem(owner, name, item);
        system.getSaveLoad().saveItem(owner.getPath(), name, item);
    }

    @Override
    public void removeItem(SerializableFolderFS<T> owner, String name) {
        super.removeItem(owner, name);
        system.getSaveLoad().deleteItem(owner.getPath(), name);
    }

    @Override
    public void clearItems(SerializableFolderFS<T> owner) {
        super.clearItems(owner);
    }

    @Override
    public SerializableFolderFS<T> addFolder(SerializableFolderFS<T> owner, String name) {
        final SerializableFolderFS<T> result = super.addFolder(owner, name);
        system.getSaveLoad().saveFolder(result.getPath());
        return result;
    }

    @Override
    public void removeFolder(SerializableFolderFS<T> owner, String name) {
        owner.getFoldersRaw().remove(name);
    }

    @Override
    protected void removeSingleFolder(SerializableFolderFS<T> owner, String name) {
        super.removeSingleFolder(owner, name);
        system.getFolderPool().remove(SerializableFileSystem.getPath(owner.getPath(), name));
        system.getSaveLoad().deleteFolder(SerializableFileSystem.getPath(owner.getPath(), name));
    }

    @Override
    public void clearFolders(SerializableFolderFS<T> owner) {
        final List<String> names = ImmutableList.copyOf(owner.getFoldersRaw().keySet());
        owner.getFoldersRaw().clear();
        system.getSaveLoad().startCommit();
        final String path = owner.getPath();
        for(String name : names) {
            system.getFolderPool().remove(SerializableFileSystem.getPath(owner.getPath(), name));
            system.getSaveLoad().deleteFolder(path == null ? name : path + "/" + name);
        }
        system.getSaveLoad().commit();
    }
}
