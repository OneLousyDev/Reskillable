package com.lousiesmods.skillsreborn.skill;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class SkillAttack extends Skill {
    public SkillAttack() {
        super(new ResourceLocation(MOD_ID, "attack"), new ResourceLocation("textures/blocks/stonebrick.png"));
    }
}