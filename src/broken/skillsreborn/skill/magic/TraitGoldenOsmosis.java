package com.lousiesmods.skillsreborn.skill.magic;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

import com.lousiesmods.skillsreborn.api.unlockable.Trait;
import com.lousiesmods.skillsreborn.base.ExperienceHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TraitGoldenOsmosis extends Trait {
    public TraitGoldenOsmosis() {
        super(new ResourceLocation(MOD_ID, "golden_osmosis"), 3, 2, new ResourceLocation(MOD_ID, "magic"),
                10, "reskillable:magic|20", "reskillable:mining|6", "reskillable:gathering|6", "reskillable:attack|6");
    }

    @Override
    public void onPlayerTick(PlayerTickEvent event) {
        if (!event.player.world.isRemote) {
            tryRepair(event.player, event.player.getHeldItemMainhand());
            tryRepair(event.player, event.player.getHeldItemOffhand());
            event.player.inventory.armorInventory.forEach(stack -> tryRepair(event.player, stack));
        }
    }

    private void tryRepair(EntityPlayer player, ItemStack stack) {
        if (!stack.isEmpty()) {
            int damage = stack.getItemDamage();
            if (damage > 2) {
                Item item = stack.getItem();
                if (item.isRepairable() && item.getIsRepairable(stack, new ItemStack(Items.GOLD_INGOT))) {
                    if (ExperienceHelper.getPlayerXP(player) > 0) {
                        ExperienceHelper.drainPlayerXP(player, 1);
                        stack.setItemDamage(damage - 3);
                    }
                }
            }
        }
    }
}