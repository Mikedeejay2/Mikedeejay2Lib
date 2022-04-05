package com.mikedeejay2.mikedeejay2lib.util.asm;

import com.mikedeejay2.jsee.asm.AgentInfo;

import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * A utility class for Minecraft compatibility with <code>JSEE</code> (Java Strong Encapsulation Eliminator).
 * This class ensures that the correct plugin classloader is used to access plugin classes and avoid exceptions.
 * <p>
 * JSEE is <b>REQUIRED</b> to use this class! It must be a dependency that is shaded into your plugin's jar.
 *
 * @author Mikedeejay2
 */
public final class MinecraftJSEEAgent {
    private static final Logger LOGGER = Logger.getLogger("MinecraftJSEEAgent");

    /**
     * Private constructor, throws exception
     */
    private MinecraftJSEEAgent() {
        throw new UnsupportedOperationException("MinecraftJSEEAgent cannot be initialized");
    }

    /**
     * <code>agentmain()</code> method, called by Java instrumentation
     *
     * @param args            Arguments to process. Default indices:
     *                        <ul>
     *                            <li><i>0</i> - UUID to locate the {@link AgentInfo} of the agent</li>
     *                            <li><i>1</i> - The name of the plugin's main <code>Class</code></li>
     *                        </ul>
     * @param instrumentation The {@link Instrumentation} used to instrument the JVM
     */
    public static void agentmain(String args, Instrumentation instrumentation) {
        ClassLoader minecraftClassLoader = null;
        String[] sArgs = args.split(" ");
        for(Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            if(clazz.getName().equals(sArgs[1])) {
                minecraftClassLoader = clazz.getClassLoader();
                break;
            }
        }
        if(minecraftClassLoader == null) {
            LOGGER.severe("Could not find Minecraft ClassLoader! This should not be possible!");
            return;
        }
        try {
            Class<?> clazz = Class.forName("com.mikedeejay2.mikedeejay2lib.util.asm.MinecraftJSEE",
                                           true, minecraftClassLoader);
            Method method = clazz.getDeclaredMethod("agentMainInternal", String.class, Instrumentation.class);
            method.setAccessible(true);
            MethodHandle handle = MethodHandles.lookup().unreflect(method);
            handle.invoke(args, instrumentation);
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }
}
