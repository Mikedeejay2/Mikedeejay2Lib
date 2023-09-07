package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class AutoFileSystemModifier<T extends ConfigurationSerializable> extends BaseSystemModifier<T> {
    public AutoFileSystemModifier(SerializableFileSystem<T> system) {
        super(system);
    }

    @Override
    public void addObject(SerializableFolderFS<T> owner, String name, T item) {
        super.addObject(owner, name, item);
        system.getSaveLoad().saveObject(owner.getPath(), name, item);
    }

    @Override
    public void removeObject(SerializableFolderFS<T> owner, String name) {
        super.removeObject(owner, name);
        system.getSaveLoad().deleteObject(owner.getPath(), name);
    }

    @Override
    public void clearObjects(SerializableFolderFS<T> owner) {
        system.getSaveLoad().startCommit();
        super.clearObjects(owner);
        system.getSaveLoad().commit();
    }

    @Override
    public SerializableFolderFS<T> addFolder(SerializableFolderFS<T> owner, String name) {
        final SerializableFolderFS<T> result = super.addFolder(owner, name);
        system.getSaveLoad().saveFolder(result.getPath());
        return result;
    }

    @Override
    public void removeFolder(SerializableFolderFS<T> folder) {
        system.getSaveLoad().startCommit();
        super.removeFolder(folder);
        system.getSaveLoad().commit();
    }

    @Override
    protected void removeSingleFolder(SerializableFolderFS<T> folder) {
        super.removeSingleFolder(folder);
        system.getFolderPool().remove(folder.getPath());
        system.getSaveLoad().deleteFolder(folder.getPath());
    }

    @Override
    public void clearFolders(SerializableFolderFS<T> owner) {
        system.getSaveLoad().startCommit();
        super.clearFolders(owner);
        system.getSaveLoad().commit();
    }
}
