package com.lousiesmods.skillsreborn.skill;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class SkillAgility extends Skill {
    public SkillAgility() {
        super(new ResourceLocation(MOD_ID, "agility"), new ResourceLocation("textures/blocks/gravel.png"));
    }
}