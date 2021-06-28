package com.mikedeejay2.mikedeejay2lib.text.chat.animation;

import com.mikedeejay2.mikedeejay2lib.text.chat.ChatSystem;
import com.mikedeejay2.mikedeejay2lib.text.chat.ChatSlide;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import com.mikedeejay2.mikedeejay2lib.text.title.TitleFrame;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The <code>BukkitRunnable</code> that acts as the animation runtime of <code>ChatSlides</code>
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
    protected long wait;
    // The current frame index of the animation
    protected int index;
    // The list of command senders to receive the slides
    protected CommandSender[] receivers;
    // Whether this is the runtimes first run or not
    protected boolean firstRun;

    public ChatAnimRuntime(ChatSystem system, CommandSender... receivers)
    {
        this.system = system;
        this.slides = system.getSlides();
        this.wait = 0;
        this.index = 0;
        this.receivers = receivers;
        this.firstRun = true;
    }

    /**
     * Overridden <code>onRun()</code> method that animates through the slides
     */
    @Override
    public void onRun()
    {
        if(slides.size() == 0) return;
        wait += period;
        if(firstRun)
        {
            if(wait > delay)
            {
                if(index < slides.size()) index = 0;
                firstRun = false;
                ChatSlide slide = slides.get(index);
                slide.print(receivers);
                ++index;
                wait = 0;
            }
            return;
        }
        if(index >= slides.size())
        {
            this.cancel();
            return;
        }
        long curWait = slides.get(index - 1 < 0 ? slides.size() - 1 : index - 1).getPeriod();
        if(wait < curWait) return;
        int framePass = (int) (wait / curWait);
        wait = 0;
        ChatSlide slide = slides.get(index);
        slide.print(receivers);
        index += framePass;
    }
}
