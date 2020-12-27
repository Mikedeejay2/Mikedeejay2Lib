package com.mikedeejay2.mikedeejay2lib.text.chat.modules;

import com.mikedeejay2.mikedeejay2lib.text.chat.ChatSection;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

/**
 * <tt>ChatModule</tt> is a module that allows the implementation of extra data and features into
 * a <tt>ChatSection</tt>. Any or all methods in this can be implemented.
 *
 * @author Mikedeejay2
 */
public interface ChatModule
{
    /**
     * Method that is called when the <tt>ChatSection</tt> is printed to a <tt>CommandSender</tt>
     *
     * @param section  The <tt>ChatSection</tt> that is being printed
     * @param receiver The <tt>CommandSender</tt> that is receiving the message
     */
    default void onPrint(ChatSection section, CommandSender receiver) {}

    /**
     * Method that is called when the <tt>ChatSection</tt> is baked
     *
     * @param section    The <tt>ChatSection</tt> that is being baked
     * @param components The current array of <tt>BaseComonents</tt> that have been baked
     */
    default void onBake(ChatSection section, BaseComponent[] components) {}
}
