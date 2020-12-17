package com.mikedeejay2.mikedeejay2lib.chat.modules;

import com.mikedeejay2.mikedeejay2lib.chat.section.ChatSection;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

public abstract class ChatModule
{
    public void onPrint(ChatSection section, CommandSender receiver) {}

    public void onBake(ChatSection section, BaseComponent[] components) {}
}
