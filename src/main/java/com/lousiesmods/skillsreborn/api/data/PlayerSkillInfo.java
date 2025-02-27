package com.lousiesmods.skillsreborn.api.data;

import com.lousiesmods.skillsreborn.api.ReskillableRegistries;
import com.lousiesmods.skillsreborn.api.skill.Skill;
import com.lousiesmods.skillsreborn.api.unlockable.Ability;
import com.lousiesmods.skillsreborn.api.unlockable.IAbilityEventHandler;
import com.lousiesmods.skillsreborn.api.unlockable.Unlockable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class PlayerSkillInfo 
{
    private static final String TAG_LEVEL = "level";
    private static final String TAG_SKILL_POINTS = "skillPoints";
    private static final String TAG_UNLOCKABLES = "unlockables";

    public final Skill skill;

    private int level;
    private int skillPoints;
    private List<Unlockable> unlockables = new ArrayList<>();

    public PlayerSkillInfo(Skill skill)
    {
        this.skill = skill;
        level = 1;
        respec();
    }

    public void loadFromNBT(CompoundNBT cmp)
    {
        level = cmp.getInt(TAG_LEVEL);
        skillPoints = cmp.getInt(TAG_SKILL_POINTS);

        unlockables.clear();
        CompoundNBT unlockablesCmp = cmp.getCompound(TAG_UNLOCKABLES);

        for (String s : unlockablesCmp.keySet())
        {
            Optional.ofNullable(ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(s.replace(".", ":"))))
                    .ifPresent(unlockables::add);
        }
    }

    public void saveToNBT(CompoundNBT cmp)
    {
        cmp.putInt(TAG_LEVEL, level);
        cmp.putInt(TAG_SKILL_POINTS, skillPoints);

        CompoundNBT unlockablesCmp = new CompoundNBT();

        for (Unlockable u : unlockables)
        {
            unlockablesCmp.putBoolean(u.getKey(), true);
        }

        cmp.put(TAG_UNLOCKABLES, unlockablesCmp);
    }

    public int getLevel()
    {
        if (level <= 0)
        {
            level = 1;
        }

        if (level > skill.getCap())
        {
            level = skill.getCap();
        }

        return level;
    }

    public void setLevel(int level)
    {
        int interval = skill.getSkillPointInterval();
        skillPoints += level / interval - this.level / interval;
        this.level = level;
    }

    public int getRank()
    {
        return skill.getRank(level);
    }

    public int getSkillPoints()
    {
        return skillPoints;
    }

    public boolean isCapped()
    {
        return level >= skill.getCap();
    }

    public int getLevelUpCost()
    {
        return skill.getLevelUpCost(level);
    }

    public boolean isUnlocked(Unlockable u)
    {
        return unlockables.contains(u);
    }

    public void addAbilities(Set<Ability> abilities)
    {
        for (Unlockable u : unlockables)
        {
            if (u instanceof Ability)
            {
                abilities.add((Ability) u);
            }
        }
    }

    //TODO decide if this should just call setLevel(level + 1);
    public void levelUp()
    {
        level++;
        if (level % skill.getSkillPointInterval() == 0)
        {
            skillPoints++;
        }
    }

    public void lock(Unlockable u, PlayerEntity p)
    {
        skillPoints += u.getCost();
        unlockables.remove(u);
        u.onLock(p);
    }

    public void unlock(Unlockable u, PlayerEntity p)
    {
        skillPoints -= u.getCost();
        unlockables.add(u);
        u.onUnlock(p);
    }

    public void respec()
    {
        unlockables.clear();
        skillPoints = level / skill.getSkillPointInterval();
    }

    public void forEachEventHandler(Consumer<IAbilityEventHandler> consumer)
    {
        unlockables.forEach(u ->
        {
            if (u.isEnabled() && u instanceof IAbilityEventHandler)
            {
                consumer.accept((IAbilityEventHandler) u);
            }
        });
    }
}