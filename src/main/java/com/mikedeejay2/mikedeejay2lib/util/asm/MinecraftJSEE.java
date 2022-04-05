package com.mikedeejay2.mikedeejay2lib.util.asm;

import com.mikedeejay2.jsee.asm.AgentInfo;
import com.mikedeejay2.jsee.asm.ByteUtils;
import com.mikedeejay2.jsee.asm.LateBindAttacher;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * A utility class for Minecraft compatibility with <code>JSEE</code> (Java Strong Encapsulation Eliminator).
 * In the case of utilizing JSEE with Minecraft plugins, {@link MinecraftJSEE#initJSEE(Class)} should be called to
 * properly configure JSEE.
 * <p>
 * JSEE is <b>REQUIRED</b> to use this class! It must be a dependency that is shaded into your plugin's jar.
 *
 * @author Mikedeejay2
 */
public class MinecraftJSEE {
    private static final Logger LOGGER = Logger.getLogger("MinecraftJSEE");

    /**
     * Private constructor, throws exception
     */
    private MinecraftJSEE() {
        throw new UnsupportedOperationException("MinecraftJSEE cannot be initialized");
    }

    /**
     * Initialize the JSEE environment for this Minecraft plugin. This method does
     * <ul>
     *     <li>Set the agent to {@link MinecraftJSEEAgent}</li>
     *     <li>Add the plugin's main class name to the arguments sent to the agent. This is used to retrieve the
     *     plugin's <code>PluginClassLoader</code></li>
     * </ul>
     *
     * @param pluginClass The plugin's main <code>Class/code>
     */
    public static void initJSEE(Class<? extends BukkitPlugin> pluginClass) {
        AgentInfo.setDefaultAgent(MinecraftJSEEAgent.class);
        AgentInfo.setDefaultArgs(pluginClass.getName());
    }

    /**
     * Internal agent main. Called by {@link MinecraftJSEEAgent#agentmain(String, Instrumentation)}
     *
     * @param args            Arguments to process. Default indices:
     *                        <ul>
     *                            <li><i>0</i> - UUID to locate the {@link AgentInfo} of the agent</li>
     *                            <li><i>1</i> - The name of the plugin's main <code>Class</code></li>
     *                        </ul>
     * @param instrumentation The {@link Instrumentation} used to instrument the JVM
     */
    private static void agentMainInternal(String args, Instrumentation instrumentation) {
        String[] sArgs = args.split(" ");
        UUID uuid = UUID.fromString(sArgs[0]);
        AgentInfo info = LateBindAttacher.INFO_MAP.remove(uuid);
        if(info == null) {
            LOGGER.severe("AgentInfo not found for " +  uuid);
            return;
        }
        for(ClassFileTransformer transformer : info.getTransformers()) {
            instrumentation.addTransformer(transformer);
        }

        for(Class<?> toRedefine : info.getClassesToRedefine()) {
            try {
                instrumentation.redefineClasses(new ClassDefinition(toRedefine, ByteUtils.getBytesFromClass(toRedefine)));
            } catch(UnmodifiableClassException | ClassNotFoundException | VerifyError e) {
                LOGGER.severe("Failed redefine for class " + toRedefine.getName());
                e.printStackTrace();
            }
        }
    }
}
