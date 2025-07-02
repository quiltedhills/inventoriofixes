package net.quiltedhills.inventoriofixes.mixin.minecraft;

import me.lizardofoz.inventorio.util.MixinHelpers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * An edge case with Inventorio's displayed tools is that when you have a valid tool
 * selected in hotbar *while* there is a tool active from Inventorio's tool slots,
 * the game will correctly use the displayed tool but incorrectly use the hotbar tool's mining speed.
 * This patch checks for an active Inventorio tool to force using its tool speed instead.
 */
@Mixin(Inventory.class)
public abstract class InventoryMixin {
    @Inject(
            method = "getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F",
            at = @At("HEAD"),
            cancellable = true
    )
    private void patchGetDestroySpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        Inventory self = (Inventory)(Object) this;

        MixinHelpers.withInventoryAddon(self.player, (inventorioAddon) -> {
            ItemStack mainHandStack = inventorioAddon.getDisplayedMainHandStack();
            if (mainHandStack != null) {
                cir.setReturnValue(mainHandStack.getDestroySpeed(state));
            }
        });
    }
}
