package com.lousiesmods.skillsreborn.advancement.trait;


import com.lousiesmods.skillsreborn.api.unlockable.Unlockable;
import com.lousiesmods.skillsreborn.lib.LibMisc;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.util.ResourceLocation;

public class UnlockUnlockableCriterionInstance extends CriterionInstance
{
    private final Unlockable unlockable;

    public UnlockUnlockableCriterionInstance(Unlockable unlockable)
    {
        super(new ResourceLocation(LibMisc.MOD_ID, "unlockable"), EntityPredicate.AndPredicate.ANY_AND);
        this.unlockable = unlockable;
    }

    public boolean test(Unlockable other)
    {
        return unlockable == other;
    }
}
