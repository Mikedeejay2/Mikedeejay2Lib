package com.mikedeejay2.mikedeejay2lib.util.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public final class FastItemMeta {
    private static final MethodHandle META_HANDLE;

    static {
        try {
            final Field metaField = ItemStack.class.getDeclaredField("meta");
            metaField.setAccessible(true);
            META_HANDLE = MethodHandles.lookup().unreflectGetter(metaField);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemMeta getItemMeta(ItemStack itemStack) throws Throwable {
        return itemStack.getClass() == ItemStack.class ? // Ensure it's not a CraftItemStack (doesn't use meta field)
               (ItemMeta) META_HANDLE.invokeExact(itemStack) :
               itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;
    }
}
