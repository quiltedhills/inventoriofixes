package net.quiltedhills.inventoriofixes.mixin.inventorio;

import me.lizardofoz.inventorio.player.inventory.PlayerInventoryInjects;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Inventorio seems to calculate the maximum allowed stack size by comparing how much the item can stack
 * and how much the slot itself should be able to hold, choosing the larger of the two values.
 * I am not really sure of the reasons behind this but this breaks stack sizes for mods like TFC,
 * where individual item types have different stack sizes.
 * This patch excludes checking for the slot's capacity, making the stack size check only use
 * the target item's maximum stack size.
 */
@Mixin(PlayerInventoryInjects.class)
public abstract class PlayerInventoryInjectsMixin {
    @ModifyVariable(
            method = "transfer(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At(value = "STORE", ordinal = 0),
            index = 3,
            remap = false
    )
    private int patchMaxStackSize(int originalI, ItemStack sourceStack, @NotNull ItemStack targetStack) {
        return targetStack.getMaxStackSize();
    }
}
