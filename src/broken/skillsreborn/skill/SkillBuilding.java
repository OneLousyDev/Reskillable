package com.lousiesmods.skillsreborn.skill;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class SkillBuilding extends Skill {
    public SkillBuilding() {
        super(new ResourceLocation(MOD_ID, "building"), new ResourceLocation("textures/blocks/brick.png"));
    }
}