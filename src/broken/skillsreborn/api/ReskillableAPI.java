package com.lousiesmods.skillsreborn.api;

import com.lousiesmods.skillsreborn.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.*;
import com.lousiesmods.skillsreborn.api.requirement.logic.LogicParser;
import com.lousiesmods.skillsreborn.api.skill.SkillConfig;
import com.lousiesmods.skillsreborn.api.unlockable.Unlockable;
import com.lousiesmods.skillsreborn.api.unlockable.UnlockableConfig;
import com.lousiesmods.skillsreborn.api.requirement.*;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.util.Objects;

public class ReskillableAPI {
    private static ReskillableAPI instance;
    private IModAccess modAccess;
    private RequirementRegistry requirementRegistry;

    public ReskillableAPI(IModAccess modAccess) {
        this.modAccess = modAccess;
        this.requirementRegistry = new RequirementRegistry();
        requirementRegistry.addRequirementHandler("adv", input -> new AdvancementRequirement(new ResourceLocation(input)));
        requirementRegistry.addRequirementHandler("trait", input -> {
            Unlockable unlockable = ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(input));
            if (unlockable == null) {
                throw new RequirementException("Unlockable '" + input + "' not found.");
            }
            return new TraitRequirement(unlockable);
        });
        requirementRegistry.addRequirementHandler("unobtainable", input -> new UnobtainableRequirement());
        requirementRegistry.addRequirementHandler("none", input -> new NoneRequirement()); //Makes it so other requirements are ignored

        //Logic Requirements
        requirementRegistry.addRequirementHandler("not", LogicParser::parseNOT);
        requirementRegistry.addRequirementHandler("and", LogicParser::parseAND);
        requirementRegistry.addRequirementHandler("nand", LogicParser::parseNAND);
        requirementRegistry.addRequirementHandler("or", LogicParser::parseOR);
        requirementRegistry.addRequirementHandler("nor", LogicParser::parseNOR);
        requirementRegistry.addRequirementHandler("xor", LogicParser::parseXOR);
        requirementRegistry.addRequirementHandler("xnor", LogicParser::parseXNOR);
    }

    public static ReskillableAPI getInstance() {
        return Objects.requireNonNull(instance, "Calling Reskillable API Instance before it's creation");
    }

    public static void setInstance(ReskillableAPI reskillableAPI) {
        instance = reskillableAPI;
    }

    public SkillConfig getSkillConfig(ResourceLocation name) {
        return modAccess.getSkillConfig(name);
    }

    public UnlockableConfig getTraitConfig(ResourceLocation name, int x, int y, int cost, String[] defaultRequirements) {
        return modAccess.getUnlockableConfig(name, x, y, cost, defaultRequirements);
    }

    public void syncPlayerData(EntityPlayer entityPlayer, PlayerData playerData) {
        modAccess.syncPlayerData(entityPlayer, playerData);
    }

    public AdvancementProgress getAdvancementProgress(EntityPlayer entityPlayer, Advancement advancement) {
        return modAccess.getAdvancementProgress(entityPlayer, advancement);
    }

    public RequirementRegistry getRequirementRegistry() {
        return requirementRegistry;
    }

    public void log(Level warn, String s) {
        modAccess.log(warn, s);
    }
}