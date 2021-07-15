package com.lousiesmods.skillsreborn.advancement.skilllevel;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import com.lousiesmods.skillsreborn.lib.LibMisc;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SkillLevelCriterionInstance extends CriterionInstance
{
    private final Skill skill;
    private final int level;

    public SkillLevelCriterionInstance(@Nullable Skill skill, int level)
    {
        super(new ResourceLocation(LibMisc.MOD_ID, "skill_level"), EntityPredicate.AndPredicate.ANY_AND);

        this.skill = skill;
        this.level = level;
    }

    public boolean test(final Skill skill, final int level)
    {
        return (this.skill == null || this.skill == skill) && level >= this.level;
    }
}
