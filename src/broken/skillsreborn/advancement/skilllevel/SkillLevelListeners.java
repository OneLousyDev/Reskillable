package com.lousiesmods.skillsreborn.advancement.skilllevel;

import com.lousiesmods.skillsreborn.advancement.CriterionListeners;
import com.lousiesmods.skillsreborn.api.skill.Skill;
import net.minecraft.advancements.PlayerAdvancements;

public class SkillLevelListeners extends CriterionListeners<SkillLevelCriterionInstance> {
    public SkillLevelListeners(PlayerAdvancements playerAdvancements) {
        super(playerAdvancements);
    }

    public void trigger(final Skill skill, final int level) {
        trigger(instance -> instance.test(skill, level));
    }
}
