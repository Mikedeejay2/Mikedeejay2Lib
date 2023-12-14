package com.mikedeejay2.mikedeejay2lib.util.head;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.UUID;

/**
 * A utility class for modifying the texture of {@link SkullMeta}. Uses a combination of reflection and
 * {@link MethodHandle MethodHandles} to interact with the item meta.
 *
 * @author Mikedeejay2
 */
public final class SkullMetaModifier {
    private static final Field PROFILE_FIELD;
    private static final MethodHandle SET_PROFILE_HANDLE;

    static {
        final Class<?> clazz = new ItemStack(Material.PLAYER_HEAD).getItemMeta().getClass();
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            final Method setProfileMethod = clazz.getDeclaredMethod("setProfile", GameProfile.class);
            final Field profileField = clazz.getDeclaredField("profile");
            setProfileMethod.setAccessible(true);
            profileField.setAccessible(true);

            SET_PROFILE_HANDLE = lookup.unreflect(setProfileMethod);
            PROFILE_FIELD = profileField;
        } catch(NoSuchMethodException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Private constructor, should not be used
     */
    private SkullMetaModifier() {
        throw new UnsupportedOperationException("HeadModifier can not be initialized as an object");
    }

    /**
     * Get the Base64 string of a {@link SkullMeta}. The Base64 string represents the texture of the head.
     *
     * @param meta The {@link SkullMeta} to get the Base64 string from
     * @return The Base64 string if it exists
     */
    public static @Nullable String getHeadBase64(SkullMeta meta) {
        GameProfile profile;
        try {
            profile = (GameProfile) PROFILE_FIELD.get(meta);
        } catch(IllegalAccessException exception) {
            exception.printStackTrace();
            return null;
        }
        final Collection<Property> properties = profile.getProperties().get("textures");
        for(Property property : properties) {
            String value = property.getValue();
            if(value.equals("textures")) {
                return property.getName();
            }
        }
        return null;
    }

    /**
     * Set the Base64 string of a {@link SkullMeta}. The Base64 string represents the texture of the head. It is usually
     * obtained from a website online or another head's Base64 string.
     *
     * @param meta   The {@link SkullMeta} to set
     * @param base64 The Base64 string to use
     */
    public static void setHeadBase64(SkullMeta meta, String base64) {
        final GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64));
        try {
            SET_PROFILE_HANDLE.invoke(meta, profile);
        } catch(Throwable exception) {
            exception.printStackTrace();
        }
    }
}
