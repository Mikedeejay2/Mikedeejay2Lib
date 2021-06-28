package com.mikedeejay2.mikedeejay2lib.text.chat.modules;

import com.mikedeejay2.mikedeejay2lib.text.chat.ChatSection;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

/**
 * <code>ChatModule</code> is a module that allows the implementation of extra data and features into
 * a <code>ChatSection</code>. Any or all methods in this can be implemented.
 *
 * @author Mikedeejay2
 */
public interface ChatModule
{
    /**
     * Method that is called when the <code>ChatSection</code> is printed to a <code>CommandSender</code>
     *
     * @param section  The <code>ChatSection</code> that is being printed
     * @param receiver The <code>CommandSender</code> that is receiving the message
     */
    default void onPrint(ChatSection section, CommandSender receiver) {}

    /**
     * Method that is called when the <code>ChatSection</code> is baked
     *
     * @param section    The <code>ChatSection</code> that is being baked
     * @param components The current array of <code>BaseComonents</code> that have been baked
     */
    default void onBake(ChatSection section, BaseComponent[] components) {}
}
