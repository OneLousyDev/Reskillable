package com.lousiesmods.skillsreborn.api.requirement.logic.impl;

import com.lousiesmods.skillsreborn.api.requirement.Requirement;
import com.lousiesmods.skillsreborn.api.requirement.RequirementComparision;
import com.lousiesmods.skillsreborn.api.requirement.logic.DoubleRequirement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

public class XORRequirement extends DoubleRequirement {
    public XORRequirement(Requirement left, Requirement right) {
        super(left, right);
    }

    @Override
    public boolean achievedByPlayer(EntityPlayer player) {
        return leftAchieved(player) != rightAchieved(player);
    }

    @Override
    protected String getFormat() {
        return new TextComponentTranslation("reskillable.requirements.format.xor").getUnformattedComponentText();
    }

    @Override
    public RequirementComparision matches(Requirement o) {
        if (o instanceof ORRequirement) {
            ORRequirement other = (ORRequirement) o;
            RequirementComparision left = getLeft().matches(other.getLeft());
            RequirementComparision right = getRight().matches(other.getRight());
            boolean same = left.equals(right);
            if (same && left.equals(RequirementComparision.EQUAL_TO)) {
                return RequirementComparision.EQUAL_TO;
            }

            //Check to see if they were just written in the opposite order
            RequirementComparision leftAlt = getLeft().matches(other.getRight());
            RequirementComparision rightAlt = getRight().matches(other.getLeft());
            boolean altSame = leftAlt.equals(rightAlt);
            if (altSame && leftAlt.equals(RequirementComparision.EQUAL_TO)) {
                return RequirementComparision.EQUAL_TO;
            }

            //XOR specific check

        }
        return RequirementComparision.NOT_EQUAL;
    }
}