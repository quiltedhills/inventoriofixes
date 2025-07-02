package net.quiltedhills.inventoriofixes.mixin.tconstruct;

import me.lizardofoz.inventorio.player.PlayerInventoryAddon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.modifiers.upgrades.general.SoulboundModifier;

/**
 * Items that have TConstruct's Soulbound modifier will completely disappear on death
 * due to the mod never seeing Inventorio addon slots.
 * This patch repeats the item copying logic specifically for these addon slots.
 */
@Mixin(value = SoulboundModifier.class, remap = false)
public abstract class SoulboundModifierMixin {
    @Inject(
            method = "onPlayerDropItems(Lnet/minecraftforge/event/entity/living/LivingDropsEvent;)V",
            at = @At("TAIL")
    )
    private void patchOnPlayerDropItems(LivingDropsEvent event, CallbackInfo ci) {
        LivingEntity entity = event.getEntityLiving();
        if (event.isCanceled() || entity.getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || !(entity instanceof Player)) {
            return;
        }

        PlayerInventoryAddon addonInv = PlayerInventoryAddon.Companion.getInventoryAddon((Player) entity);
        if (addonInv == null) return;
        SoulboundModifier self = (SoulboundModifier)(Object) this;

        for (ItemStack stack : addonInv.stacks) {
            if (!stack.is(TinkerTags.Items.MODIFIABLE)) continue;
            ToolStack tool = ToolStack.from(stack);
            if (tool.getModifierLevel(self) <= 0) continue;

            tool.getPersistentData().remove(TConstruct.getResource("soulbound_slot"));
            ((Player) entity).getInventory().add(stack);
        }
    }
}
