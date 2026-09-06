package com.fodk.gemcolony.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {

    private static final KeyMapping KEY_MAPPING_BUBBLE_GEM = new KeyMapping("key.gemcolony.bubble_gem",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, KeyMapping.Category.MISC);
    public static final Lazy<KeyMapping> PRESS_BUBBLE_GEM = Lazy.of(() -> KEY_MAPPING_BUBBLE_GEM);


    public static void register() {

    }
}
