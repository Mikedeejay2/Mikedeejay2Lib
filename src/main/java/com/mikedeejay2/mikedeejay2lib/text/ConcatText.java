package com.mikedeejay2.mikedeejay2lib.text;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConcatText implements Text {
    protected Text text1;
    protected Text text2;

    protected ConcatText(Text text1, Text text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    @Override
    public String get(Player player) {
        return text1.get(player) + text2.get(player);
    }

    @Override
    public String get(CommandSender sender) {
        return text1.get(sender) + text2.get(sender);
    }

    @Override
    public String get(String locale) {
        return text1.get(locale) + text2.get(locale);
    }

    @Override
    public String get() {
        return text1.get() + text2.get();
    }

    @Override
    public Text placeholder(PlaceholderFormatter formatter) {
        text1.placeholder(formatter);
        text2.placeholder(formatter);
        return this;
    }

    @Override
    public ConcatText clone() {
        ConcatText text;
        try {
            text = (ConcatText) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        text.text1 = this.text1.clone();
        text.text2 = this.text2.clone();
        return text;
    }
}
