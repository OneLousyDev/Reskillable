package com.lousiesmods.skillsreborn.skill;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class SkillFarming extends Skill {
    public SkillFarming() {
        super(new ResourceLocation(MOD_ID, "farming"), new ResourceLocation("textures/blocks/dirt.png"));
    }
}