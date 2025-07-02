package net.quiltedhills.inventoriofixes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("inventoriofixes")
public class InventorioFixes
{
    public static final String MOD_ID = "inventoriofixes";
    public static final String MOD_NAME = "Inventorio Fixes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public InventorioFixes()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }
}
