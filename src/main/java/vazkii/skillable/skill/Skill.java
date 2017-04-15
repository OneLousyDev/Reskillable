package vazkii.skillable.skill;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.text.translation.I18n;
import vazkii.skillable.skill.base.Unlockable;

public abstract class Skill implements Comparable<Skill> {

	private final String name;
	private final int index;
	private final Block background;
	
	public final List<Unlockable> unlockables = new ArrayList();
	
	public Skill(String name, int index, Block background) {
		this.name = name;
		this.index = index;
		this.background = background;
		initUnlockables();
	}
	
	public abstract void initUnlockables();
	
	public String getKey() {
		return name;
	}
	
	public String getName() {
		return I18n.translateToLocal("skillable.skill." + getKey());
	}
	
	public int getIndex() {
		return index;
	}
	
	public Block getBackground() {
		return background;
	}

	@Override
	public int compareTo(Skill o) {
		return getIndex() - o.getIndex();
	}
	
}
