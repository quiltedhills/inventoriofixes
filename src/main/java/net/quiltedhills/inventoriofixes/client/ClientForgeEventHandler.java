package net.quiltedhills.inventoriofixes.client;

import com.mojang.blaze3d.platform.InputConstants;
import me.lizardofoz.inventorio.client.ui.InventorioScreen;
import me.lizardofoz.inventorio.packet.InventorioNetworking;
import net.dries007.tfc.client.TFCKeyBindings;
import net.dries007.tfc.network.PacketHandler;
import net.dries007.tfc.network.StackFoodPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.PacketDistributor;
import net.quiltedhills.inventoriofixes.InventorioFixes;

@Mod.EventBusSubscriber(modid = InventorioFixes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventHandler {
    @SubscribeEvent
    public static void onKeyEvent(InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Screen currentScreen = minecraft.screen;

        if (ExtraKeybindings.OPEN_INVENTORIO_INVENTORY.isDown()) {

            if (!(currentScreen instanceof InventoryScreen) && currentScreen instanceof AbstractContainerScreen<?>) {
                currentScreen.onClose();
            } else {
                InventorioNetworking.getInstance().c2sOpenInventorioScreen();
                InventorioScreen.shouldOpenVanillaInventory = false;
            }
            return;
        }

        if (ExtraKeybindings.OPEN_VANILLA_INVENTORY.isDown()) {
            if (!(currentScreen instanceof InventorioScreen) && currentScreen instanceof AbstractContainerScreen<?>) {
                currentScreen.onClose();
            } else {
                assert minecraft.player != null;
                minecraft.setScreen(new InventoryScreen(minecraft.player));
                InventorioScreen.shouldOpenVanillaInventory = true;
            }
        }

    }

    @SubscribeEvent
    public static void onScreenKey(ScreenEvent.KeyboardKeyPressedEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        Screen currentScreen = minecraft.screen;
        boolean shouldPlaySound = false;


        if (ExtraKeybindings.SWITCH_INVENTORY.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            if (currentScreen instanceof InventorioScreen) {
                currentScreen.onClose();
                minecraft.setScreen(new InventoryScreen(minecraft.player));
                InventorioScreen.shouldOpenVanillaInventory = true;
                shouldPlaySound = true;
            } else if (currentScreen instanceof InventoryScreen) {
                InventorioNetworking.getInstance().c2sOpenInventorioScreen();
                InventorioScreen.shouldOpenVanillaInventory = false;
                shouldPlaySound = true;
            }
        }
        if (shouldPlaySound)
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));


        // Unrelated to any of my own keybinds - piling in support for TFC's Stack Food keybind
        if (FMLLoader.getLoadingModList().getModFileById("tfc") != null) {
            if (TFCKeyBindings.STACK_FOOD.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode())) && event.getScreen() instanceof InventorioScreen inv) {
                Slot slot = inv.getSlotUnderMouse();
                if (slot != null) {
                    PacketHandler.send(PacketDistributor.SERVER.noArg(), new StackFoodPacket(slot.index));
                }
            }
        }
    }
}
