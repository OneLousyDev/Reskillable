package com.lousiesmods.skillsreborn.advancement.trait;

import com.lousiesmods.skillsreborn.advancement.CriterionListeners;
import com.lousiesmods.skillsreborn.api.unlockable.Unlockable;
import net.minecraft.advancements.PlayerAdvancements;

public class UnlockUnlockableListeners extends CriterionListeners<UnlockUnlockableCriterionInstance> {
    public UnlockUnlockableListeners(PlayerAdvancements playerAdvancements) {
        super(playerAdvancements);
    }

    public void trigger(final Unlockable unlockable) {
        trigger(instance -> instance.test(unlockable));
    }
}
