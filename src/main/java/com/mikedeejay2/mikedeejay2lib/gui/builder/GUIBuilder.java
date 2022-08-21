package com.mikedeejay2.mikedeejay2lib.gui.builder;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;

public final class GUIBuilder {
    private final BukkitPlugin plugin;
    private final GUIContainer gui;

    private GUIBuilder(BukkitPlugin plugin) {
        this.gui = new GUIContainer(plugin, "Unnamed GUI", 3);
        this.plugin = plugin;
    }

    public static GUIBuilder builder(BukkitPlugin plugin) {
        return new GUIBuilder(plugin);
    }
}
