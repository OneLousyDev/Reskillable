package com.lousiesmods.skillsreborn.skill;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class SkillGathering extends Skill {
    public SkillGathering() {
        super(new ResourceLocation(MOD_ID, "gathering"), new ResourceLocation("textures/blocks/log_oak.png"));
    }
}