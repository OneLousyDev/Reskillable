package com.lousiesmods.skillsreborn.skill.mining;

import com.lousiesmods.skillsreborn.api.unlockable.Trait;
import com.lousiesmods.skillsreborn.base.ConditionHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class TraitObsidianSmasher extends Trait {
    public TraitObsidianSmasher() {
        super(new ResourceLocation(MOD_ID, "obsidian_smasher"), 1, 2, new ResourceLocation(MOD_ID, "mining"),
                4, "reskillable:mining|16");
    }

    @Override
    public void getBreakSpeed(BreakSpeed event) {
        if (event.isCanceled()) {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        IBlockState state = event.getState();

        if (state.getBlock() == Blocks.OBSIDIAN && ConditionHelper.hasRightTool(player, state, "pickaxe", ToolMaterial.DIAMOND.getHarvestLevel())) {
            event.setNewSpeed(event.getOriginalSpeed() * 10);
        }
    }
}