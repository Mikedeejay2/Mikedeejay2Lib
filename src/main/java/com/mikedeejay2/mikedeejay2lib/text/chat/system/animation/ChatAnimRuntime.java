package com.mikedeejay2.mikedeejay2lib.text.chat.system.animation;

import com.mikedeejay2.mikedeejay2lib.text.chat.system.ChatSystem;
import com.mikedeejay2.mikedeejay2lib.text.chat.system.slide.ChatSlide;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The <tt>BukkitRunnable</tt> that acts as the animation runtime of <tt>ChatSlides</tt>
 *
 * @author Mikedeejay2
 */
public class ChatAnimRuntime extends EnhancedRunnable
{
    // The chat system of this runnable
    protected ChatSystem system;
    // The list of slides that this runtime animates through
    protected List<ChatSlide> slides;
    // The current wait time of the animation
    protected long curWait;
    // The current frame index of the animation
    protected int index;
    // The list of command senders to receive the slides
    protected CommandSender[] receivers;

    public ChatAnimRuntime(ChatSystem system, CommandSender... receivers)
    {
        this.system = system;
        this.slides = system.getSlides();
        this.curWait = 0;
        this.index = 0;
        this.receivers = receivers;
    }

    /**
     * Overridden <tt>onRun()</tt> method that animates through the slides
     */
    @Override
    public void onRun()
    {
        curWait += period;
        if(index >= slides.size())
        {
            this.cancel();
            return;
        }
        ChatSlide curSlide = slides.get(index);
        long amtToWait = slides.get(index - 1 < 0 ? slides.size() - 1 : index - 1).getPeriod();
        if(curWait < amtToWait) return;
        curSlide.print(receivers);
        ++index;
        curWait = 0;
    }
}
