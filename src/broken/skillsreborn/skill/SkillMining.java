package com.lousiesmods.skillsreborn.skill;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class SkillMining extends Skill {
    public SkillMining() {
        super(new ResourceLocation(MOD_ID, "mining"), new ResourceLocation("textures/blocks/stone.png"));
    }
}