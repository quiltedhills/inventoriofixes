package net.quiltedhills.inventoriofixes.mixin.inventorio;

import me.lizardofoz.inventorio.player.PlayerInventoryAddon;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Adds a forced update for the currently displayed tool slot.
 * This makes things like durability update correctly!
 */
@Mixin(value = PlayerInventoryAddon.class, remap = false)
public abstract class PlayerInventoryAddonMixin {
    @Unique private int inventoriofixes$cachedToolIndex = -1;

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void patchTickHead(CallbackInfo ci) {
        PlayerInventoryAddon self = (PlayerInventoryAddon)(Object) this;

        if (self.getPlayer().swinging && !self.getDisplayTool().isEmpty()) {
            inventoriofixes$cachedToolIndex = self.findFittingToolBeltIndex(self.getDisplayTool());
        }
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void patchTickTail(CallbackInfo ci) {
        long ms = Util.getMillis();
        PlayerInventoryAddon self = (PlayerInventoryAddon)(Object) this;

        if (self.getDisplayToolTimeStamp() <= ms) {
            inventoriofixes$cachedToolIndex = -1;
        } else {
            if (inventoriofixes$cachedToolIndex < 0 || inventoriofixes$cachedToolIndex >= self.toolBelt.size()) return;
            ItemStack actualToolItem = self.toolBelt.get(inventoriofixes$cachedToolIndex);
            if (!self.getDisplayTool().isEmpty() && !ItemStack.matches(self.getDisplayTool(), actualToolItem)) {
                self.setDisplayTool(actualToolItem.copy());
            }
        }
    }
}
