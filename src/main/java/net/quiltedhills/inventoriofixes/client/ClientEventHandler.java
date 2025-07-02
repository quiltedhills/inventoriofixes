package net.quiltedhills.inventoriofixes.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.quiltedhills.inventoriofixes.InventorioFixes;

@Mod.EventBusSubscriber(modid = InventorioFixes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEventHandler {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(ExtraKeybindings.OPEN_INVENTORIO_INVENTORY);
        ClientRegistry.registerKeyBinding(ExtraKeybindings.OPEN_VANILLA_INVENTORY);
        ClientRegistry.registerKeyBinding(ExtraKeybindings.SWITCH_INVENTORY);
    }
}
