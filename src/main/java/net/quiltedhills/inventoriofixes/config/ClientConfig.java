package net.quiltedhills.inventoriofixes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public final ForgeConfigSpec.BooleanValue forceKeybindsRememberState;
    public final ForgeConfigSpec.EnumValue<ForceKeybindsGuiBehavior> forceKeybindsGuiBehavior;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("keybinds");

        forceKeybindsRememberState = builder.comment("Should hitting a Force-Open-Inventory key change the state of the last opened inventory for minecraft's standard Open Inventory keybind?").define("forceKeybindsRememberState", true);
        forceKeybindsGuiBehavior = builder.comment(
                "Behavior of the Force-Open-Inventory keybinds when a menu is already open.",
                "CLOSE = Both hotkeys will always close the open inventory",
                "SWITCH = Open the menu associated with the keybind, but do nothing if it is already open",
                "SWITCH_OR_CLOSE = Same as SWITCH, except close the associated menu if it is already open"
        ).defineEnum("forceKeybindsGuiBehavior", ForceKeybindsGuiBehavior.CLOSE);

        builder.pop();
    }
    public enum ForceKeybindsGuiBehavior {
        CLOSE,
        SWITCH,
        SWITCH_OR_CLOSE
    }
}
