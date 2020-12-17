package com.mikedeejay2.mikedeejay2lib.chat.section;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.modules.ChatModule;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ChatSection
{
    protected final PluginBase plugin;
    protected List<ChatModule> modules;
    protected String text;
    protected boolean baked;
    protected BaseComponent[] components;

    public ChatSection(PluginBase plugin)
    {
        this.plugin = plugin;
        this.modules = new ArrayList<>();
        this.text = "";
        this.baked = false;
    }

    public ChatSection addModule(ChatModule module)
    {
        modules.add(module);
        baked = false;
        return this;
    }

    public ChatSection addText(String text)
    {
        this.text += Chat.chat(text);
        baked = false;
        return this;
    }

    public ChatSection clearText()
    {
        this.text = "";
        baked = false;
        return this;
    }

    protected void bake()
    {
        components = plugin.chat().getBaseComponentArray(text);
        modules.forEach(module -> module.onBake(this, components));
        baked = true;
    }

    public void print(CommandSender sender)
    {
        if(!baked) bake();
        sender.spigot().sendMessage(components);
    }
}
