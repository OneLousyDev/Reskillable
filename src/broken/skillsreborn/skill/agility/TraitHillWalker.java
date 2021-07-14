package com.lousiesmods.skillsreborn.skill.agility;

import com.lousiesmods.skillsreborn.api.data.PlayerData;
import com.lousiesmods.skillsreborn.api.data.PlayerDataHandler;
import com.lousiesmods.skillsreborn.api.unlockable.Trait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class TraitHillWalker extends Trait {
    public TraitHillWalker() {
        super(new ResourceLocation(MOD_ID, "hillwalker"), 2, 2, new ResourceLocation(MOD_ID, "agility"), 8, "reskillable:agility|32");
        if (FMLCommonHandler.instance().getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        PlayerData data = null;
        if (player != null) {
            data = PlayerDataHandler.get(player);
        }

        if (data != null && data.getSkillInfo(getParentSkill()).isUnlocked(this)) {
            if (player.isSneaking()) {
                player.stepHeight = 0.9F;
            } else {
                player.stepHeight = 1.0625F;
            }
        }
    }
}