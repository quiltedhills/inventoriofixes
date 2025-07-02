package net.quiltedhills.inventoriofixes.mixin.inventorio;

import me.lizardofoz.inventorio.player.inventory.PlayerInventoryExtension;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PlayerInventoryExtension.class)
public interface PlayerInventoryExtensionAccessor {
    @Accessor("lastTrackedStacksState")
    List<ItemStack> getLastTrackedStacksState();
}