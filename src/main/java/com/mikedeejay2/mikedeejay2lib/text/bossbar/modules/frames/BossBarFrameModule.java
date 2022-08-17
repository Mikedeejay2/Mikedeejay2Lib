package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.BossBarModule;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Special <code>BossBarModule</code> that adds frame like capabilities to modules.
 * <p>
 * Frames can be added through {@link BossBarFrameModule#addFrame(long, Object)} or similar.
 *
 * @param <T> The data type of the frame
 */
public abstract class BossBarFrameModule<T> implements BossBarModule {
    /**
     * The list of map entries of frames
     */
    protected List<Map.Entry<Long, T>> frames;

    /**
     * The current index of the frames (runtime variable)
     */
    protected int curIndex;

    /**
     * The current wait time of the frames (runtime variables)
     */
    protected int wait;

    /**
     * Whether this module should loop the frames or not
     */
    protected boolean loop;

    /**
     * Construct a new <code>BossBarFrameModule</code>
     *
     * @param loop Whether this module should loop the frames or not
     */
    public BossBarFrameModule(boolean loop) {
        this.frames = new ArrayList<>();
        this.loop = loop;
    }

    /**
     * Processes the ticks, runs the frame if applicable.
     * <p>
     * This method should not be overridden, use {@link BossBarFrameModule#onFrame(BossBarSystem, long, Object)} instead.
     *
     * @see BossBarFrameModule#onFrame(BossBarSystem, long, Object)
     * @param system The <code>BossBarSystem</code> being ticked
     */
    @Override
    public final void onTick(BossBarSystem system) {
        ++wait;
        if(curIndex >= frames.size()) {
            if(loop) {
                curIndex = 0;
            } else return;
        }
        long curWait = frames.get(curIndex - 1 < 0 ? frames.size() - 1 : curIndex - 1).getKey();
        if(wait < curWait) return;
        Map.Entry<Long, T> entry = frames.get(curIndex);
        T value = entry.getValue();
        long period = entry.getKey();
        onFrame(system, period, value);
        ++curIndex;
        wait -= curWait;
    }

    /**
     * Method that is called when a frame should be processed
     *
     * @param system The <code>BossBarSystem</code> that should be modified
     * @param period The period of the current frame
     * @param value  The value of the frame
     */
    public abstract void onFrame(BossBarSystem system, long period, T value);

    /**
     * Add a frame to this module
     *
     * @param period The period (Time this frame and the next frame)
     * @param value  The value of the frame (Different depending on the module)
     * @param index  The index to add the module to
     * @return The current <code>BossBarFrameModule</code>
     */
    public BossBarFrameModule<T> addFrame(long period, T value, int index) {
        frames.add(index, new AbstractMap.SimpleEntry<>(period, value));
        return this;
    }

    /**
     * Add a frame to this module
     *
     * @param period The period (Time this frame and the next frame)
     * @param value The value of the frame (Different depending on the module)
     * @return The current <code>BossBarFrameModule</code>
     */
    public BossBarFrameModule<T> addFrame(long period, T value) {
        frames.add(new AbstractMap.SimpleEntry<>(period, value));
        return this;
    }

    /**
     * Remove a frame based off of the index of the frame
     *
     * @param index The index to remove the frame at
     * @return The current <code>BossBarFrameModule</code>
     */
    public BossBarFrameModule<T> removeFrame(int index) {
        frames.remove(index);
        return this;
    }

    /**
     * Remove a frame based off of a reference of the value of the frame
     *
     * @param value The value of the frame to remove
     * @return The current <code>BossBarFrameModule</code>
     */
    public BossBarFrameModule<T> removeFrame(T value) {
        for(Map.Entry<Long, T> entry : frames) {
            T curValue = entry.getValue();
            if(!curValue.equals(value)) continue;
            frames.remove(entry);
            break;
        }
        return this;
    }

    /**
     * Get the list of <code>Map.Entry</code> for all frames
     *
     * @return The list of frames
     */
    public List<Map.Entry<Long, T>> getFrames() {
        return frames;
    }
}
