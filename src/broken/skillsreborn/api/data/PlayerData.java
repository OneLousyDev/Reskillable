package com.lousiesmods.skillsreborn.api.data;

import com.lousiesmods.skillsreborn.api.ReskillableAPI;
import com.lousiesmods.skillsreborn.api.ReskillableRegistries;
import com.lousiesmods.skillsreborn.api.requirement.Requirement;
import com.lousiesmods.skillsreborn.api.requirement.RequirementCache;
import com.lousiesmods.skillsreborn.api.skill.Skill;
import com.lousiesmods.skillsreborn.api.unlockable.Ability;
import com.lousiesmods.skillsreborn.api.unlockable.IAbilityEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Consumer;

public class PlayerData {
    private static final String TAG_SKILLS_CMP = "SkillLevels";
    private final boolean client;
    private final RequirementCache requirementCache;
    public WeakReference<EntityPlayer> playerWR;
    private Map<Skill, PlayerSkillInfo> skillInfo = new HashMap<>();

    public PlayerData(EntityPlayer player) {
        playerWR = new WeakReference<>(player);
        client = player.getEntityWorld().isRemote;
        requirementCache = RequirementCache.getCache(player);

        ReskillableRegistries.SKILLS.getValuesCollection().forEach(s -> skillInfo.put(s, new PlayerSkillInfo(s)));

        load();
    }

    public PlayerSkillInfo getSkillInfo(Skill s) {
        return skillInfo.get(s);
    }

    public Collection<PlayerSkillInfo> getAllSkillInfo() {
        return skillInfo.values();
    }

    public boolean hasAnyAbilities() {
        return !getAllAbilities().isEmpty();
    }

    public Set<Ability> getAllAbilities() {
        Set<Ability> set = new TreeSet<>();
        skillInfo.values().forEach(info -> info.addAbilities(set));
        return set;
    }

    public boolean matchStats(RequirementHolder holder) {
        return playerWR.get() == null || holder.getRequirements().stream().allMatch(this::requirementAchieved);
    }

    //helper method to access the requirement cache
    public boolean requirementAchieved(Requirement requirement) {
        return getRequirementCache().requirementAchieved(requirement);
    }

    public final RequirementCache getRequirementCache() {
        return requirementCache;
    }

    public void load() {
        if (!client) {
            EntityPlayer player = playerWR.get();

            if (player != null) {
                NBTTagCompound cmp = PlayerDataHandler.getDataCompoundForPlayer(player);
                loadFromNBT(cmp);
            }
        }
    }

    public void save() {
        if (!client) {
            EntityPlayer player = playerWR.get();

            if (player != null) {
                NBTTagCompound cmp = PlayerDataHandler.getDataCompoundForPlayer(player);
                saveToNBT(cmp);
            }
        }
    }

    public void sync() {
        if (!client) {
            EntityPlayer player = playerWR.get();
            ReskillableAPI.getInstance().syncPlayerData(player, this);
        }
    }

    public void saveAndSync() {
        save();
        sync();
    }

    public void loadFromNBT(NBTTagCompound cmp) {
        NBTTagCompound skillsCmp = cmp.getCompoundTag(TAG_SKILLS_CMP);
        for (PlayerSkillInfo info : skillInfo.values()) {
            String key = info.skill.getKey();
            if (skillsCmp.hasKey(key)) {
                NBTTagCompound infoCmp = skillsCmp.getCompoundTag(key);
                info.loadFromNBT(infoCmp);
            }
        }
    }

    public void saveToNBT(NBTTagCompound cmp) {
        NBTTagCompound skillsCmp = new NBTTagCompound();

        for (PlayerSkillInfo info : skillInfo.values()) {
            String key = info.skill.getKey();
            NBTTagCompound infoCmp = new NBTTagCompound();
            info.saveToNBT(infoCmp);
            skillsCmp.setTag(key, infoCmp);
        }

        cmp.setTag(TAG_SKILLS_CMP, skillsCmp);
    }

    // Event Handlers

    public void tickPlayer(PlayerTickEvent event) {
        forEachEventHandler(h -> h.onPlayerTick(event));
    }

    public void blockDrops(HarvestDropsEvent event) {
        forEachEventHandler(h -> h.onBlockDrops(event));
    }

    public void mobDrops(LivingDropsEvent event) {
        forEachEventHandler(h -> h.onMobDrops(event));
    }

    public void breakSpeed(BreakSpeed event) {
        forEachEventHandler(h -> h.getBreakSpeed(event));
    }

    public void attackMob(LivingHurtEvent event) {
        forEachEventHandler(h -> h.onAttackMob(event));
    }

    public void hurt(LivingHurtEvent event) {
        forEachEventHandler(h -> h.onHurt(event));
    }

    public void rightClickBlock(RightClickBlock event) {
        forEachEventHandler(h -> h.onRightClickBlock(event));
    }

    public void enderTeleport(EnderTeleportEvent event) {
        forEachEventHandler(h -> h.onEnderTeleport(event));
    }

    public void killMob(LivingDeathEvent event) {
        forEachEventHandler(h -> h.onKillMob(event));
    }

    public void forEachEventHandler(Consumer<IAbilityEventHandler> consumer) {
        skillInfo.values().forEach(info -> info.forEachEventHandler(consumer));
    }
}