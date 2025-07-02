package net.quiltedhills.inventoriofixes.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

import static net.quiltedhills.inventoriofixes.InventorioFixes.MOD_NAME;

public class ExtraKeybindings {
    public static final KeyMapping OPEN_VANILLA_INVENTORY = new KeyMapping(
            "inventoriofixes.key.open_vanilla_inventory",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            MOD_NAME
    );
    public static final KeyMapping OPEN_INVENTORIO_INVENTORY = new KeyMapping(
            "inventoriofixes.key.open_inventorio_inventory",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            MOD_NAME
    );
    public static final KeyMapping SWITCH_INVENTORY = new KeyMapping(
            "inventoriofixes.key.switch_inventory",
            KeyConflictContext.GUI,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            MOD_NAME
    );
}