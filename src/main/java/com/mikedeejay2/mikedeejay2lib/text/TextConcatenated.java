package com.mikedeejay2.mikedeejay2lib.text;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextConcatenated implements Text {
    protected Text text1;
    protected Text text2;

    protected TextConcatenated(Text text1, Text text2) {
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
    public TextConcatenated clone() {
        TextConcatenated text;
        try {
            text = (TextConcatenated) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        text.text1 = this.text1.clone();
        text.text2 = this.text2.clone();
        return text;
    }
}
