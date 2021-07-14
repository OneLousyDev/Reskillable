package com.lousiesmods.skillsreborn.skill;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import net.minecraft.util.ResourceLocation;

import static com.lousiesmods.skillsreborn.lib.LibMisc.MOD_ID;

public class SkillMagic extends Skill {
    public SkillMagic() {
        super(new ResourceLocation(MOD_ID, "magic"), new ResourceLocation("textures/blocks/end_stone.png"));
    }
}