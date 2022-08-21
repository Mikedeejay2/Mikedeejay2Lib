package com.mikedeejay2.mikedeejay2lib.text;

import org.bukkit.entity.Player;

public class LiteralText implements Text {
    public static final LiteralText EMPTY = new LiteralText("");

    protected final String text;

    protected LiteralText(String text) {
        this.text = text;
    }

    @Override
    public String getText(Player player) {
        return text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return getText();
    }
}
