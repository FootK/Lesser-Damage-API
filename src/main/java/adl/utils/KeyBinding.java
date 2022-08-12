package adl.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY = "key.category.infutil";
    public static final String KEY_MORELIVES = "key.infutil.morelives";
    public static final String KEY_MOREDAMADIV = "key.infutil.damadiv";

    public static final KeyMapping MORELIVES = new KeyMapping(KEY_MORELIVES, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, KEY_CATEGORY);
    public static final KeyMapping DAMADIV = new KeyMapping(KEY_MOREDAMADIV, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY);
}
