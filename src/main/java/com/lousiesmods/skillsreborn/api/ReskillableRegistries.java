package com.lousiesmods.skillsreborn.api;

import com.lousiesmods.skillsreborn.api.skill.Skill;
import com.lousiesmods.skillsreborn.api.unlockable.Unlockable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ReskillableRegistries {
    public final static IForgeRegistry<Skill> SKILLS = GameRegistry.findRegistry(Skill.class);
    public final static IForgeRegistry<Unlockable> UNLOCKABLES = GameRegistry.findRegistry(Unlockable.class);
}