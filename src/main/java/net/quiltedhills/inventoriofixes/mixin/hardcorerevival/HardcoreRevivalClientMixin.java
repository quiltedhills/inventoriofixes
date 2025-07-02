package net.quiltedhills.inventoriofixes.mixin.hardcorerevival;

import me.lizardofoz.inventorio.client.ui.InventorioScreen;
import net.blay09.mods.balm.api.event.client.OpenScreenEvent;
import net.blay09.mods.hardcorerevival.client.HardcoreRevivalClient;
import net.minecraft.client.gui.screens.Screen;
import net.quiltedhills.inventoriofixes.InventorioFixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Constructor;

/**
 * When Hardcore Revival prompts the player to hit their inventory key to bring the KO screen up,
 * it will register the keypress by checking whether the GUI that is currently open is an instance of the Inventory class.
 * As the Inventorio inventory screen is not a vanilla Inventory, it will be ignored despite being summoned
 * with the exact same keybind.
 * This patch makes the Inventorio inventory screen also valid for this, alongside the vanilla inventory.
 */
@Mixin(value = HardcoreRevivalClient.class, remap = false)
public abstract class HardcoreRevivalClientMixin {
    @Shadow
    private static boolean isKnockedOut() { return false; }

    @Inject(
            method = "onOpenScreen(Lnet/blay09/mods/balm/api/event/client/OpenScreenEvent;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void patchOnOpenScreen(OpenScreenEvent event, CallbackInfo ci) {
        if (isKnockedOut() && event.getScreen() instanceof InventorioScreen) {
            try {
                Class<?> koScreenClass = Class.forName("net.blay09.mods.hardcorerevival.client.KnockoutScreen");
                Constructor<?> constructor = koScreenClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                event.setScreen((Screen) constructor.newInstance());
                ci.cancel();
            } catch (Exception e) {
                InventorioFixes.LOGGER.error("Failed to instantiate KnockoutScreen class from mod Hardcore Revival", e);
            }
        }
    }
}
