package net.quiltedhills.inventoriofixes.mixin.create_sa;

import net.mcreator.createstuffadditions.procedures.FuelAndFillPrProcedure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fix Create S&A's fuel procedure applying an empty "{}" nbt tag to offhand items
 */
@Mixin(value = FuelAndFillPrProcedure.class, remap = false)
public abstract class FuelAndFillPrProcedureMixin {
    @Inject(
            method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void patchExecute(Event event, LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        ItemStack offhandItem = entity instanceof LivingEntity ? ((LivingEntity) entity).getOffhandItem() : ItemStack.EMPTY;

        boolean isFillable = offhandItem.is(ItemTags.create(new ResourceLocation("create_sa:fillable")));
        boolean isFuelable = offhandItem.is(ItemTags.create(new ResourceLocation("create_sa:fuelable")));
        if (!isFillable && !isFuelable) ci.cancel();
    }
}
