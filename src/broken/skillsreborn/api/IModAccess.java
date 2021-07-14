package com.lousiesmods.skillsreborn.api;

import com.lousiesmods.skillsreborn.api.data.PlayerData;
import com.lousiesmods.skillsreborn.api.skill.SkillConfig;
import com.lousiesmods.skillsreborn.api.unlockable.UnlockableConfig;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

public interface IModAccess {
    SkillConfig getSkillConfig(ResourceLocation name);

    UnlockableConfig getUnlockableConfig(ResourceLocation name, int x, int y, int cost, String[] defaultRequirements);

    void syncPlayerData(EntityPlayer entityPlayer, PlayerData playerData);

    AdvancementProgress getAdvancementProgress(EntityPlayer entityPlayer, Advancement advancement);

    void log(Level warn, String s);
}