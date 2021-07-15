package com.lousiesmods.skillsreborn.api.requirement.logic;

import com.lousiesmods.skillsreborn.api.requirement.Requirement;

import javax.annotation.Nonnull;
import java.util.List;

public interface OuterRequirement {
    @Nonnull
    List<Class<? extends Requirement>> getInternalTypes();
}