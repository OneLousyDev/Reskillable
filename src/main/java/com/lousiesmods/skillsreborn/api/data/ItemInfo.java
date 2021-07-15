package com.lousiesmods.skillsreborn.api.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.Objects;

public class ItemInfo extends NBTLockKey
{
    private Item item;

    public ItemInfo(Item item)
    {
        this(item, null);
    }

    public ItemInfo(Item item, CompoundNBT tag)
    {
        super(tag);
        this.item = item;
    }

    public ItemInfo(ItemStack stack)
    {
        this(stack.getItem(), stack.getTag());
    }

    @Override
    public LockKey getNotFuzzy()
    {
        return isNotFuzzy() ? this : new ItemInfo(item);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        if (!(o instanceof ItemInfo))
        {
            return false;
        }

        ItemInfo other = (ItemInfo) o;
        if (getItem() != other.getItem())
        {
            return false;
        }

        if (getTag() == null)
        {
            return other.getTag() == null && (getMetadata() == OreDictionary.WILDCARD_VALUE || other.getMetadata() == OreDictionary.WILDCARD_VALUE || getMetadata() == other.getMetadata());
        }

        return getTag().equals(other.getTag()) && (getMetadata() == OreDictionary.WILDCARD_VALUE || other.getMetadata() == OreDictionary.WILDCARD_VALUE || getMetadata() == other.getMetadata());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(item, tag);
    }

    public Item getItem()
    {
        return item;
    }

    public int getMetadata()
    {
        return metadata;
    }
}
