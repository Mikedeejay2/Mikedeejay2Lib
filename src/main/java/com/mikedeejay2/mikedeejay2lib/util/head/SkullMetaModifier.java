package com.mikedeejay2.mikedeejay2lib.util.head;

import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.Collection;
import java.util.UUID;

/**
 * A utility class for modifying the texture of {@link SkullMeta}.
 *
 * @author Mikedeejay2
 */
public final class SkullMetaModifier {
    private static final String URL_PREFIX = "{\"textures\":{\"SKIN\":{\"url\":\"";
    private static final String URL_SUFFIX = "\"}}}";
    private static final HeadBase64Accessor ACCESSOR = MinecraftVersion.check(">=1.18.1") ?
                                                       new HeadBase64AccessorImpl() :
                                                       new HeadBase64AccessorLegacy();

    /**
     * Private constructor, should not be used
     */
    private SkullMetaModifier() {
        throw new UnsupportedOperationException("SkullMetaModifier can not be initialized as an object");
    }

    /**
     * Get the texture of a {@link SkullMeta} object as a Base64 String
     * <p>
     * <strong>If you are using 1.18.1+, the following methods are preferred for better performance (avoids conversion
     * to/from base64: {@link SkullMetaModifier#getHeadUrl(SkullMeta)},
     * {@link SkullMetaModifier#getHeadUrlAsString(SkullMeta)}</strong>
     *
     * @param meta The {@link SkullMeta} to get the Base64 string from
     * @return The head URL as a Base64 string (if it exists)
     */
    public static @Nullable String getHeadBase64(SkullMeta meta) {
        return ACCESSOR.getHeadBase64(meta);
    }

    /**
     * Get the texture of a {@link SkullMeta} object as a URL String
     *
     * @param meta The {@link SkullMeta} to get the URL string from
     * @return The head URL as a string (if it exists)
     */
    public static @Nullable String getHeadUrlAsString(SkullMeta meta) {
        return ACCESSOR.getHeadUrlAsString(meta);
    }

    /**
     * Get the texture of a {@link SkullMeta} object as a URL
     *
     * @param meta The {@link SkullMeta} to get the URL from
     * @return The head URL (if it exists)
     */
    public static @Nullable URL getHeadUrl(SkullMeta meta) {
        return ACCESSOR.getHeadUrl(meta);
    }

    /**
     * Set the texture for a {@link SkullMeta} using a Base64 String
     * <p>
     * <strong>If you are using 1.18.1+, the following methods are preferred for better performance (avoids conversion
     * to/from base64: {@link SkullMetaModifier#setHeadUrl(SkullMeta, String)},
     * {@link SkullMetaModifier#setHeadUrl(SkullMeta, URL)}</strong>
     *
     * @param meta The {@link SkullMeta} to set
     * @param base64 The Base64 string to use
     */
    public static void setHeadBase64(SkullMeta meta, String base64) {
        ACCESSOR.setHeadBase64(meta, base64);
    }

    /**
     * Set the texture for a {@link SkullMeta} using a URL String
     *
     * @param meta  The {@link SkullMeta} to set
     * @param url The URL string to use
     */
    public static void setHeadUrl(SkullMeta meta, String url) {
        ACCESSOR.setHeadUrl(meta, url);
    }

    /**
     * Set the texture for a {@link SkullMeta} using a URL
     *
     * @param meta  The {@link SkullMeta} to set
     * @param url The URL to use
     */
    public static void setHeadUrl(SkullMeta meta, URL url) {
        ACCESSOR.setHeadUrl(meta, url);
    }

    /**
     * Helper method to convert a Base64 String to a URL
     *
     * @param base64 The input Base64 String
     * @return The converted URL
     */
    public static URL convertBase64ToUrl(String base64) {
        return convertStringToUrl(convertBase64ToString(base64));
    }

    /**
     * Helper method to convert a String URL to a URL
     *
     * @param url The input URL String
     * @return The output URL
     */
    public static URL convertStringToUrl(String url) {
        try {
            return new URI(url).toURL();
        } catch(MalformedURLException | URISyntaxException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Helper method to convert from a Base64 String to a URL String
     *
     * @param base64 The input Base64 String
     * @return The output URL String
     */
    public static String convertBase64ToString(String base64) {
        final String decoded = new String(Base64.getDecoder().decode(base64));
        return decoded.substring(URL_PREFIX.length(), decoded.length() - URL_SUFFIX.length());
    }

    /**
     * Helper method to convert from a URL to a Base64 String
     *
     * @param url The input URL
     * @return The output Base64 String
     */
    public static String convertUrlToBase64(URL url) {
        return convertUrlToBase64(url.toExternalForm());
    }

    /**
     * Helper method to convert from a URL String to a Base64 String
     *
     * @param url The input URL String
     * @return The output Base64 String
     */
    public static String convertUrlToBase64(String url) {
        return Base64.getEncoder().encodeToString((URL_PREFIX + url + URL_SUFFIX).getBytes());
    }


    /**
     * Interface for {@link SkullMeta} Base64 texture access
     */
    private interface HeadBase64Accessor {
        @Nullable String getHeadBase64(SkullMeta meta);
        @Nullable String getHeadUrlAsString(SkullMeta meta);
        @Nullable URL getHeadUrl(SkullMeta meta);
        void setHeadBase64(SkullMeta meta, String base64);
        void setHeadUrl(SkullMeta meta, String url);
        void setHeadUrl(SkullMeta meta, URL url);
    }

    /**
     * Legacy Base64 (<1.18.1) {@link SkullMeta} texture access using {@link GameProfile}. Uses a combination of
     * reflection and {@link MethodHandle MethodHandles} to interact with the item meta.
     */
    private static class HeadBase64AccessorLegacy implements HeadBase64Accessor {
        private final Field profileField;
        private final MethodHandle setProfileHandle;

        public HeadBase64AccessorLegacy() {
            final Class<?> clazz = new ItemStack(Material.PLAYER_HEAD).getItemMeta().getClass();
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            try {
                final Method setProfileMethod = clazz.getDeclaredMethod("setProfile", GameProfile.class);
                final Field profileField = clazz.getDeclaredField("profile");
                setProfileMethod.setAccessible(true);
                profileField.setAccessible(true);

                this.setProfileHandle = lookup.unreflect(setProfileMethod);
                this.profileField = profileField;
            } catch(NoSuchMethodException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public @Nullable String getHeadBase64(SkullMeta meta) {
            GameProfile profile;
            try {
                profile = (GameProfile) profileField.get(meta);
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

        @Override
        public @Nullable String getHeadUrlAsString(SkullMeta meta) {
            return convertBase64ToString(getHeadBase64(meta));
        }

        @Override
        public @Nullable URL getHeadUrl(SkullMeta meta) {
            return convertBase64ToUrl(getHeadBase64(meta));
        }

        @Override
        public void setHeadBase64(SkullMeta meta, String base64) {
            final GameProfile profile = new GameProfile(UUID.randomUUID(), "");
            profile.getProperties().put("textures", new Property("textures", base64));
            try {
                setProfileHandle.invoke(meta, profile);
            } catch(Throwable exception) {
                exception.printStackTrace();
            }
        }

        @Override
        public void setHeadUrl(SkullMeta meta, String url) {
            setHeadBase64(meta, convertUrlToBase64(url));
        }

        @Override
        public void setHeadUrl(SkullMeta meta, URL url) {
            final String base64 = new String(Base64.getDecoder().decode(url.toExternalForm()));
            setHeadBase64(meta, base64);
        }
    }

    /**
     * 1.18.1+ Base64 {@link SkullMeta} texture access using {@link PlayerProfile}
     */
    private static class HeadBase64AccessorImpl implements HeadBase64Accessor {
        private final UUID randomUuid;

        public HeadBase64AccessorImpl() {
            this.randomUuid = UUID.fromString("a46fe227-32b0-4c81-9e6f-e794b62c890e");
        }

        @Override
        public @Nullable String getHeadBase64(SkullMeta meta) {
            final URL url = getHeadUrl(meta);
            return url == null ? null : convertUrlToBase64(url);
        }

        @Override
        public @Nullable String getHeadUrlAsString(SkullMeta meta) {
            final URL url = getHeadUrl(meta);
            return url == null ? null : url.toExternalForm();
        }

        @Override
        public @Nullable URL getHeadUrl(SkullMeta meta) {
            final PlayerProfile profile = meta.getOwnerProfile();
            return profile == null ? null : profile.getTextures().getSkin();
        }

        @Override
        public void setHeadBase64(SkullMeta meta, String base64) {
            setHeadUrl(meta, convertBase64ToUrl(base64));
        }

        @Override
        public void setHeadUrl(SkullMeta meta, String url) {
            setHeadUrl(meta, convertStringToUrl(url));
        }

        @Override
        public void setHeadUrl(SkullMeta meta, URL url) {
            final PlayerProfile profile = Bukkit.createPlayerProfile(randomUuid);
            final PlayerTextures textures = profile.getTextures();
            textures.setSkin(url);
            profile.setTextures(textures);
            meta.setOwnerProfile(profile);
        }
    }
}
