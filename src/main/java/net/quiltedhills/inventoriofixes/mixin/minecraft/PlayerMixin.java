package net.quiltedhills.inventoriofixes.mixin.minecraft;

import dev.gigaherz.toolbelt.client.RadialMenuScreen;
import me.lizardofoz.inventorio.player.PlayerInventoryAddon;
import me.lizardofoz.inventorio.util.MixinHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Whenever a mod tries to read the selected main hand item, Inventorio correctly steps in when a tool is displayed
 * and passes that instead of whatever is selected in the hotbar.
 * However, this does not apply to the action of changing the item in hand.
 * I believe most Inventorio-related dupe exploits to be caused by this.
 * This patch redirects any "set itemstack to main hand" actions to Inventorio tool slots, if there is one active.
 */
@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(
            method = "setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void patchSetItemSlot(EquipmentSlot pSlot, ItemStack pStack, CallbackInfo ci) {
        if (pSlot != EquipmentSlot.MAINHAND) return;

        Player self = (Player)(Object) this;
        MixinHelpers.withInventoryAddon(self, (inventorioAddon) -> {
            ItemStack mainHandStack = inventorioAddon.getDisplayedMainHandStack();
            if (mainHandStack != null) {
                int index = inventorioAddon.findFittingToolBeltIndex(mainHandStack);
                boolean canInsertIntoToolSlot = pStack.isEmpty() || PlayerInventoryAddon.Companion.getToolBeltTemplates().get(index).test(pStack, inventorioAddon);

                if (canInsertIntoToolSlot) {
                    inventorioAddon.toolBelt.set(index, pStack);
                    inventorioAddon.setDisplayTool(pStack);
                } else {
                    inventorioAddon.toolBelt.set(index, ItemStack.EMPTY);
                    inventorioAddon.setDisplayTool(ItemStack.EMPTY);
                    if (!self.getInventory().add(pStack)) {
                        self.drop(pStack, true);

                        // For some reason, the toolbelt radial menu freaks out in this specific case
                        Screen currentScreen = Minecraft.getInstance().screen;
                        if (currentScreen instanceof RadialMenuScreen) currentScreen.onClose();
                    }
                }
                ci.cancel();
            }
        });
    }
}
