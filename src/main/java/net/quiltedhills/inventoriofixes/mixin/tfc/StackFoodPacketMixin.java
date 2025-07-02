package net.quiltedhills.inventoriofixes.mixin.tfc;


import me.lizardofoz.inventorio.player.InventorioScreenHandler;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.IFood;
import net.dries007.tfc.network.StackFoodPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.quiltedhills.inventoriofixes.InventorioFixes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

/**
 * todo
 */
@Mixin(value = StackFoodPacket.class, remap = false)
public abstract class StackFoodPacketMixin {
    @Final @Shadow private int index;

    @Shadow
    protected abstract List<Slot> getStackableSlots(Slot targetSlot, List<Slot> inventorySlots);

    @Inject(
            method = "handle(Lnet/minecraftforge/network/NetworkEvent$Context;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void patchCountOverburdened(NetworkEvent.Context context, CallbackInfo ci) {
        // Check if we are in an Inventorio screen, otherwise let method run as normal
        final ServerPlayer player = context.getSender();
        if (player == null || !(player.containerMenu instanceof InventorioScreenHandler menu)) return;
        ci.cancel();

        // Mimics the base method with minor changes
        context.enqueueWork(() -> {
            if (index < 0 || index >= menu.slots.size()) return;

            Slot targetSlot = menu.getSlot(index);
            ItemStack targetStack = targetSlot.getItem();
            IFood targetCap = targetStack.getCapability(FoodCapability.CAPABILITY).resolve().orElse(null);

            if (targetCap == null || targetStack.getMaxStackSize() == targetStack.getCount() || targetCap.isRotten())
            {
                return;
            }

            List<Slot> stackableSlots = getStackableSlots(targetSlot, menu.slots);
            int currentAmount = targetStack.getCount();
            int remaining = targetStack.getMaxStackSize() - currentAmount;
            long minCreationDate = targetCap.getCreationDate();

            Iterator<Slot> slotIterator = stackableSlots.iterator();
            while (remaining > 0 && slotIterator.hasNext())
            {
                Slot slot = slotIterator.next();
                ItemStack stack = slot.getItem();
                IFood cap = stack.getCapability(FoodCapability.CAPABILITY).resolve().orElse(null);

                if (cap == null || cap.isRotten()) continue;

                if (cap.getCreationDate() < minCreationDate)
                {
                    minCreationDate = cap.getCreationDate();
                }

                if (remaining < stack.getCount())
                {
                    currentAmount += remaining;
                    stack.shrink(remaining);
                    remaining = 0;
                }
                else
                {
                    currentAmount += stack.getCount();
                    remaining -= stack.getCount();
                    stack.shrink(stack.getCount());
                }
            }

            targetStack.setCount(currentAmount);
            targetCap.setCreationDate(minCreationDate);

            menu.broadcastChanges();
        });
    }
}
