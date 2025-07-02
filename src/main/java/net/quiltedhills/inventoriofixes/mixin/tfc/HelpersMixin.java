package net.quiltedhills.inventoriofixes.mixin.tfc;

import me.lizardofoz.inventorio.player.PlayerInventoryAddon;
import net.dries007.tfc.common.capabilities.size.IItemSize;
import net.dries007.tfc.common.capabilities.size.ItemSizeManager;
import net.dries007.tfc.common.capabilities.size.Size;
import net.dries007.tfc.common.capabilities.size.Weight;
import net.dries007.tfc.util.Helpers;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * If TFC is installed, addon slots can be used to store very large, very heavy items
 * while bypassing exhaustion and overburdening.
 * This patch extends the usual inventory check to also include addon slots.
 */
@Mixin(value = Helpers.class, remap = false)
public abstract class HelpersMixin {
    @Inject(
            method = "countOverburdened(Lnet/minecraft/world/Container;)I",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void patchCountOverburdened(Container container, CallbackInfoReturnable<Integer> cir) {
        int count = cir.getReturnValue();
        if (count == 2) return; // Method has already concluded necessary checks


        if (!(container instanceof Inventory inventory)) return;
        PlayerInventoryAddon addonInv = PlayerInventoryAddon.Companion.getInventoryAddon(inventory.player);
        if (addonInv == null) return;

        // Mimics the base method with minor changes
        for (ItemStack stack : addonInv.stacks) {
            if (!stack.isEmpty()) {
                IItemSize size = ItemSizeManager.get(stack);
                if (size.getWeight(stack) == Weight.VERY_HEAVY && size.getSize(stack) == Size.HUGE) {
                    ++count;
                    if (count >= 2) {
                        cir.setReturnValue(2);
                        return;
                    }
                }
            }
        }
        cir.setReturnValue(count);

    }
}
