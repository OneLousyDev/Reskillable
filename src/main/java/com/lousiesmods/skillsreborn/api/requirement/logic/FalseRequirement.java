package com.lousiesmods.skillsreborn.api.requirement.logic;

import com.lousiesmods.skillsreborn.api.data.PlayerData;
import com.lousiesmods.skillsreborn.api.requirement.Requirement;
import com.lousiesmods.skillsreborn.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class FalseRequirement extends Requirement {
    public FalseRequirement() {
        this.tooltip = TextFormatting.RED + new TextComponentTranslation("reskillable.requirements.format.unobtainable").getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return false;
    }

    @Override
    public String getToolTip(PlayerData data) {
        return tooltip;
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof FalseRequirement ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FalseRequirement;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}