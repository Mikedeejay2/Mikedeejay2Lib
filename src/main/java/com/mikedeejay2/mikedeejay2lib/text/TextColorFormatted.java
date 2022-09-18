package com.mikedeejay2.mikedeejay2lib.text;

import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class TextColorFormatted implements Text {
    protected final Text text;
    protected final Set<Colors.FormatStyle> formatStyles;

    protected TextColorFormatted(Text text, Colors.FormatStyle... formatStyles) {
        this.text = text;
        this.formatStyles = new HashSet<>(Arrays.asList(formatStyles));
        Validate.isTrue(!(this.formatStyles.contains(Colors.FormatStyle.ALL) && this.formatStyles.size() > 1),
                        "Developer error, it is redundant to have more than Colors.FormatStyle.ALL");
    }

    @Override
    public String get(Player player) {
        return format(text.get(player));
    }

    @Override
    public String get(CommandSender sender) {
        return format(text.get(sender));
    }

    @Override
    public String get(String locale) {
        return format(text.get(locale));
    }

    @Override
    public String get() {
        return format(text.get());
    }

    @Override
    public Text format(Colors.FormatStyle... styles) {
        formatStyles.addAll(Arrays.asList(styles));
        return this;
    }

    private String format(String input) {
        String result = input;
        for(Colors.FormatStyle formatStyle : formatStyles) {
            result = formatStyle.format(result);
        }
        return result;
    }

    @Override
    public TextColorFormatted clone() {
        TextColorFormatted text;
        try {
            text = (TextColorFormatted) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        return text;
    }
}
