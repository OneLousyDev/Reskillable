package com.lousiesmods.skillsreborn;

import com.lousiesmods.skillsreborn.advancement.ReskillableAdvancements;
import com.lousiesmods.skillsreborn.api.event.LevelUpEvent;
import com.lousiesmods.skillsreborn.api.event.UnlockUnlockableEvent;
import com.lousiesmods.skillsreborn.api.skill.Skill;
import com.lousiesmods.skillsreborn.api.unlockable.Unlockable;
import codersafterdark.reskillable.skill.*;
import com.lousiesmods.skillsreborn.skill.agility.TraitHillWalker;
import com.lousiesmods.skillsreborn.skill.agility.TraitRoadWalk;
import com.lousiesmods.skillsreborn.skill.agility.TraitSidestep;
import com.lousiesmods.skillsreborn.skill.attack.TraitBattleSpirit;
import com.lousiesmods.skillsreborn.skill.attack.TraitNeutralissse;
import com.lousiesmods.skillsreborn.skill.building.TraitPerfectRecover;
import com.lousiesmods.skillsreborn.skill.building.TraitTransmutation;
import com.lousiesmods.skillsreborn.skill.defense.TraitEffectTwist;
import com.lousiesmods.skillsreborn.skill.defense.TraitUndershirt;
import com.lousiesmods.skillsreborn.skill.farming.TraitGreenThumb;
import com.lousiesmods.skillsreborn.skill.farming.TraitHungryFarmer;
import com.lousiesmods.skillsreborn.skill.farming.TraitMoreWheat;
import com.lousiesmods.skillsreborn.skill.gathering.TraitDropGuarantee;
import com.lousiesmods.skillsreborn.skill.gathering.TraitLuckyFisherman;
import com.lousiesmods.skillsreborn.skill.magic.TraitGoldenOsmosis;
import com.lousiesmods.skillsreborn.skill.magic.TraitSafePort;
import com.lousiesmods.skillsreborn.skill.mining.TraitFossilDigger;
import com.lousiesmods.skillsreborn.skill.mining.TraitObsidianSmasher;
import com.lousiesmods.skillsreborn.lib.LibMisc;
import com.lousiesmods.skillsreborn.skill.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ReskillableRegistryHandler {
    @SubscribeEvent
    public static void buildRegistry(RegistryEvent.NewRegistry newRegistryEvent) {
        new RegistryBuilder<Skill>().setName(new ResourceLocation(LibMisc.MOD_ID, "skill")).setType(Skill.class).create();
        new RegistryBuilder<Unlockable>().setName(new ResourceLocation(LibMisc.MOD_ID, "unlockable")).setType(Unlockable.class).create();
    }

    @SubscribeEvent
    public static void registerSkills(RegistryEvent.Register<Skill> skillRegister) {
        skillRegister.getRegistry().registerAll(
                new SkillMining(),
                new SkillGathering(),
                new SkillAttack(),
                new SkillDefense(),
                new SkillBuilding(),
                new SkillFarming(),
                new SkillAgility(),
                new SkillMagic()
        );
    }

    @SubscribeEvent
    public static void registerTraits(RegistryEvent.Register<Unlockable> unlockablesRegister) {
        unlockablesRegister.getRegistry().registerAll(
                new TraitHillWalker(),
                new TraitRoadWalk(),
                new TraitSidestep(),
                new TraitBattleSpirit(),
                new TraitNeutralissse(),
                new TraitPerfectRecover(),
                new TraitTransmutation(),
                new TraitEffectTwist(),
                new TraitUndershirt(),
                new TraitGreenThumb(),
                new TraitHungryFarmer(),
                new TraitMoreWheat(),
                new TraitDropGuarantee(),
                new TraitLuckyFisherman(),
                new TraitGoldenOsmosis(),
                new TraitSafePort(),
                new TraitFossilDigger(),
                new TraitObsidianSmasher()
        );
    }

    @SubscribeEvent
    public static void skillAdvancementHandling(LevelUpEvent.Post postEvent) {
        if (postEvent.getEntityPlayer() instanceof EntityPlayerMP) {
            ReskillableAdvancements.SKILL_LEVEL.trigger((EntityPlayerMP) postEvent.getEntityPlayer(),
                    postEvent.getSkill(), postEvent.getLevel());
        }
    }

    @SubscribeEvent
    public static void unlockableAdvancementHandling(UnlockUnlockableEvent.Post event) {
        if (event.getEntityPlayer() instanceof EntityPlayerMP) {
            ReskillableAdvancements.UNLOCK_UNLOCKABLE.trigger((EntityPlayerMP) event.getEntityPlayer(), event.getUnlockable());
        }
    }
}