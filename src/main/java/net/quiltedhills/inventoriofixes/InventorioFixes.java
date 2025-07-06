package net.quiltedhills.inventoriofixes;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

@Mod("inventoriofixes")
public class InventorioFixes
{
    public static final String MOD_ID = "inventoriofixes";
    public static final String MOD_NAME = "Inventorio Fixes";
    public static final String VERSION = "1.0.1";
    public static final Logger LOGGER = LogUtils.getLogger();

    public InventorioFixes()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(InventorioFixes.MOD_ID, "main"))
                .networkProtocolVersion(() -> VERSION)
                .clientAcceptedVersions(VERSION::equals)
                .serverAcceptedVersions(VERSION::equals)
                .simpleChannel();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }
}
