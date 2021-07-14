package com.lousiesmods.skillsreborn.skill;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class SkillDefense extends Skill {
    public SkillDefense() {
        super(new ResourceLocation(MOD_ID, "defense"), new ResourceLocation("textures/blocks/quartz_block_side.png"));
    }
}