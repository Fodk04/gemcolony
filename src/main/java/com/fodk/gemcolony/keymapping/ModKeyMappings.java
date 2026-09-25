package com.fodk.gemcolony.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {

    private static final KeyMapping KEY_MAPPING_BUBBLE_GEM = new KeyMapping("key.gemcolony.bubble_gem",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, KeyMapping.Category.MISC);
    public static final Lazy<KeyMapping> PRESS_BUBBLE_GEM = Lazy.of(() -> KEY_MAPPING_BUBBLE_GEM);

    private static final KeyMapping KEY_MAPPING_CONSTRUCTOR_UP = new KeyMapping(
            "key.gemcolony.constructor_up",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UP,
            KeyMapping.Category.MISC
    );
    public static final Lazy<KeyMapping> CONSTRUCTOR_UP =
            Lazy.of(() -> KEY_MAPPING_CONSTRUCTOR_UP);

    private static final KeyMapping KEY_MAPPING_CONSTRUCTOR_DOWN = new KeyMapping(
            "key.gemcolony.constructor_down",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_DOWN,
            KeyMapping.Category.MISC
    );
    public static final Lazy<KeyMapping> CONSTRUCTOR_DOWN =
            Lazy.of(() -> KEY_MAPPING_CONSTRUCTOR_DOWN);

    private static final KeyMapping KEY_MAPPING_CONSTRUCTOR_LEFT = new KeyMapping(
            "key.gemcolony.constructor_left",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT,
            KeyMapping.Category.MISC
    );
    public static final Lazy<KeyMapping> CONSTRUCTOR_LEFT =
            Lazy.of(() -> KEY_MAPPING_CONSTRUCTOR_LEFT);

    private static final KeyMapping KEY_MAPPING_CONSTRUCTOR_RIGHT = new KeyMapping(
            "key.gemcolony.constructor_right",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT,
            KeyMapping.Category.MISC
    );
    public static final Lazy<KeyMapping> CONSTRUCTOR_RIGHT =
            Lazy.of(() -> KEY_MAPPING_CONSTRUCTOR_RIGHT);

    private static final KeyMapping KEY_MAPPING_CONSTRUCTOR_RAISE = new KeyMapping(
            "key.gemcolony.constructor_raise",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            KeyMapping.Category.MISC
    );
    public static final Lazy<KeyMapping> CONSTRUCTOR_RAISE =
            Lazy.of(() -> KEY_MAPPING_CONSTRUCTOR_RAISE);

    private static final KeyMapping KEY_MAPPING_CONSTRUCTOR_LOWER = new KeyMapping(
            "key.gemcolony.constructor_lower",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_SHIFT,
            KeyMapping.Category.MISC
    );
    public static final Lazy<KeyMapping> CONSTRUCTOR_LOWER =
            Lazy.of(() -> KEY_MAPPING_CONSTRUCTOR_LOWER);

    private static final KeyMapping KEY_MAPPING_CONSTRUCTOR_ROTATE = new KeyMapping(
            "key.gemcolony.constructor_rotate",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            KeyMapping.Category.MISC
    );
    public static final Lazy<KeyMapping> CONSTRUCTOR_ROTATE =
            Lazy.of(() -> KEY_MAPPING_CONSTRUCTOR_ROTATE);

    private static final KeyMapping KEY_MAPPING_CONSTRUCTOR_CONFIRM = new KeyMapping(
            "key.gemcolony.constructor_confirm",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_RIGHT,
            KeyMapping.Category.MISC
    );

    private static final KeyMapping KEY_MAPPING_CONSTRUCTOR_CANCEL = new KeyMapping(
            "key.gemcolony.constructor_cancel",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_LEFT,
            KeyMapping.Category.MISC
    );

    public static final Lazy<KeyMapping> CONSTRUCTOR_CONFIRM =
            Lazy.of(() -> KEY_MAPPING_CONSTRUCTOR_CONFIRM);

    public static final Lazy<KeyMapping> CONSTRUCTOR_CANCEL =
            Lazy.of(() -> KEY_MAPPING_CONSTRUCTOR_CANCEL);

    public static void register() {

    }
}
