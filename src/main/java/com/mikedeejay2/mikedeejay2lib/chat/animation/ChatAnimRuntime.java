package com.mikedeejay2.mikedeejay2lib.chat.animation;

import com.mikedeejay2.mikedeejay2lib.chat.ChatSystem;
import com.mikedeejay2.mikedeejay2lib.chat.slide.ChatSlide;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ChatAnimRuntime extends EnhancedRunnable
{
    protected ChatSystem system;
    protected List<ChatSlide> slides;
    protected long curWait;
    protected int index;
    protected CommandSender[] receivers;

    public ChatAnimRuntime(ChatSystem system, CommandSender... receivers)
    {
        this.system = system;
        this.slides = system.getSlides();
        this.curWait = 0;
        this.index = 0;
        this.receivers = receivers;
    }

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
        long amtToWait = curSlide.getPeriod();
        if(curWait < amtToWait) return;
        curSlide.print(receivers);
        ++index;
        curWait = 0;
    }
}
