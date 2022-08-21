package com.mikedeejay2.mikedeejay2lib.text;

import org.bukkit.entity.Player;

public class TranslatableText implements Text {
    private final String key;

    protected TranslatableText(String key) {
        this.key = key;
    }

    @Override
    public String getText(Player player) {
        return null;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
