package net.quiltedhills.inventoriofixes.common;

import me.lizardofoz.inventorio.player.PlayerInventoryAddon;
import me.lizardofoz.inventorio.player.inventory.PlayerInventoryExtension;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.quiltedhills.inventoriofixes.mixin.inventorio.PlayerInventoryExtensionAccessor;

import java.util.List;

/**
 * Addon slot data seems to become reset on client when changing dimensions,
 * requiring a manual update from the server to get everything back in business.
 * This forces the server to re-send all addon slot data to the client on dimension change.
 */
@Mod.EventBusSubscriber
public class DimSwitchDesyncFix {
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
        PlayerInventoryAddon addon = PlayerInventoryAddon.Companion.getInventoryAddon(event.getPlayer());
        if (addon == null) return;
        PlayerInventoryExtensionAccessor accessor = (PlayerInventoryExtensionAccessor) (PlayerInventoryExtension) addon;

        List<ItemStack> lastSeen = accessor.getLastTrackedStacksState();
        if (lastSeen != null) {
            lastSeen.clear();
        }
    }
}

