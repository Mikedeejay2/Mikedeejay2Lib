package com.mikedeejay2.mikedeejay2lib.text;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextLiteral implements Text {
    public static final TextLiteral EMPTY = new TextLiteral("");

    protected final String text;

    protected TextLiteral(String text) {
        this.text = text;
    }

    @Override
    public String get(Player player) {
        return get();
    }

    @Override
    public String get(CommandSender sender) {
        return get();
    }

    @Override
    public String get(String locale) {
        return get();
    }

    @Override
    public String get() {
        return text;
    }

    @Override
    public String toString() {
        return get();
    }

    @Override
    public TextLiteral clone() {
        TextLiteral text;
        try {
            text = (TextLiteral) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        return text;
    }
}
