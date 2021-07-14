package com.lousiesmods.skillsreborn.api.requirement.logic;

import com.lousiesmods.skillsreborn.api.data.PlayerData;
import com.lousiesmods.skillsreborn.api.requirement.Requirement;
import com.lousiesmods.skillsreborn.api.requirement.RequirementComparision;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class TrueRequirement extends Requirement {
    public TrueRequirement() {
        this.tooltip = TextFormatting.GREEN + new TextComponentTranslation("reskillable.requirements.format.unobtainable").getUnformattedComponentText();
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer entityPlayerMP) {
        return true;
    }

    @Override
    public String getToolTip(PlayerData data) {
        //Should never be needed but probably should be set anyways
        return tooltip;
    }

    @Override
    public RequirementComparision matches(Requirement other) {
        return other instanceof TrueRequirement ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TrueRequirement;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}