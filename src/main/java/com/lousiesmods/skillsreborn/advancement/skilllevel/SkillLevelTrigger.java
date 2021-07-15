package com.lousiesmods.skillsreborn.advancement.skilllevel;

import com.lousiesmods.skillsreborn.advancement.CriterionTrigger;
import com.lousiesmods.skillsreborn.api.ReskillableRegistries;
import com.lousiesmods.skillsreborn.api.skill.Skill;
import com.lousiesmods.skillsreborn.lib.LibMisc;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SkillLevelTrigger extends CriterionTrigger<SkillLevelListeners, SkillLevelCriterionInstance>
{
    public SkillLevelTrigger()
    {
        super(new ResourceLocation(LibMisc.MOD_ID, "skill_level"), SkillLevelListeners::new);
    }

    public void trigger(ServerPlayerEntity player, Skill skill, int level)
    {
        SkillLevelListeners listeners = this.getListeners(player.getAdvancements());

        if (listeners != null)
        {
            listeners.trigger(skill, level);
        }
    }

    @Override
    public SkillLevelCriterionInstance deserialize(JsonObject json, ConditionArrayParser context)
    {
        int level = JSONUtils.getInt(json, "level");

        if (json.has("skill"))
        {
            ResourceLocation skillName = new ResourceLocation(JSONUtils.getString(json, "skill"));
            Skill skill = ReskillableRegistries.SKILLS.getValue(skillName);
            if (skill != null)
            {
                return new SkillLevelCriterionInstance(skill, level);
            }

            throw new JsonParseException("Failed to find Matching Skill for Name: " + skillName);
        }

        return new SkillLevelCriterionInstance(null, level);
    }
}
