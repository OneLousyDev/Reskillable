package com.lousiesmods.skillsreborn.api.data;

import net.minecraft.nbt.*;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class NBTLockKey implements FuzzyLockKey 
{
    protected CompoundNBT tag;

    protected NBTLockKey(CompoundNBT tag)
    {
        this.tag = tag == null || tag.isEmpty() ? null : tag;
    }

    protected static boolean similarNBT(INBT full, INBT partial) 
    {
        if (full == null)
        {
            return partial == null;
        }
        if (partial == null)
        {
            return true;
        }
        
        if (full.getId() != partial.getId())
        {
            return false;
        }
        
        if (full.equals(partial)) 
        {
            return true;
        }
        
        switch (full.getId())
        {
            case Constants.NBT.TAG_COMPOUND:
                CompoundNBT fullTag = (CompoundNBT) full;
                CompoundNBT partialTag = (CompoundNBT) partial;
                Set<String> ptKeys = partialTag.keySet();
                
                for (String partialKey : ptKeys)
                {
                    //One of the keys is missing OR the tags are different types OR they do not match
                    if (!fullTag.contains(partialKey, partialTag.getTagId(partialKey)) || !similarNBT(fullTag.getCompound(partialKey), partialTag.getCompound(partialKey)))
                    {
                        return false;
                    }
                }
                return true;
                
            case Constants.NBT.TAG_LIST:
                ListNBT fTagList = (ListNBT) full;
                ListNBT pTagList = (ListNBT) partial;

                if (fTagList.isEmpty() && !pTagList.isEmpty() || fTagList.getTagType() != pTagList.getTagType())
                {
                    return false;
                }

                for (int i = 0; i < pTagList.size(); i++)
                {
                    INBT pTag = pTagList.get(i);

                    boolean hasTag = false;
                    for (int j = 0; j < fTagList.size(); j++)
                    {
                        if (similarNBT(fTagList.get(j), pTag))
                        {
                            hasTag = true;
                            break;
                        }
                    }

                    if (!hasTag)
                    {
                        return false;
                    }
                }
                return true;

            case Constants.NBT.TAG_BYTE_ARRAY:
                byte[] fByteArray = ((ByteArrayNBT) full).getByteArray();
                byte[] pByteArray = ((ByteArrayNBT) partial).getByteArray();
                List<Integer> hits = new ArrayList<>();
                for (byte pByte : pByteArray)
                {
                    boolean hasMatch = false;
                    for (int i = 0; i < fByteArray.length; i++)
                    {
                        if (!hits.contains(i) && pByte == fByteArray[i])
                        {
                            hits.add(i);
                            hasMatch = true;
                            break;
                        }
                    }

                    if (!hasMatch)
                    {
                        return false;
                    }
                }
                return true;

            case Constants.NBT.TAG_INT_ARRAY:
                int[] fIntArray = ((IntArrayNBT) full).getIntArray();
                int[] pIntArray = ((IntArrayNBT) partial).getIntArray();
                hits = new ArrayList<>();
                for (int pInt : pIntArray)
                {
                    boolean hasMatch = false;
                    for (int i = 0; i < fIntArray.length; i++)
                    {
                        if (!hits.contains(i) && pInt == fIntArray[i])
                        {
                            hits.add(i);
                            hasMatch = true;
                            break;
                        }
                    }

                    if (!hasMatch)
                    {
                        return false;
                    }
                }
                return true;

            case Constants.NBT.TAG_LONG_ARRAY:
                long[] fLongArray = getLongArray((LongArrayNBT) full);
                long[] pLongArray = getLongArray((LongArrayNBT) partial);
                hits = new ArrayList<>();

                for (long pLong : pLongArray)
                {
                    boolean hasMatch = false;
                    for (int i = 0; i < fLongArray.length; i++)
                    {
                        if (!hits.contains(i) && pLong == fLongArray[i])
                        {
                            hits.add(i);
                            hasMatch = true;
                            break;
                        }
                    }
                    if (!hasMatch)
                    {
                        return false;
                    }
                }
                return true;

            default:
                return false;
        }
    }

    private static long[] getLongArray(LongArrayNBT tag)
    {
        String t = tag.toString();
        String[] entries = t.substring(3, t.length() - 1).split(",");//Trim the closing bracket and the [L; at the start
        long[] data = new long[entries.length];
        for (int i = 0; i < entries.length; i++)
        {
            try
            {
                data[i] = Long.parseLong(entries[i].substring(0, entries[i].length() - 1));//Trim the L
            }

            catch (Exception ignored)
            {

            }
        }

        return data;
    }

    public CompoundNBT getTag()
    {
        return this.tag;
    }

    @Override
    public boolean isNotFuzzy()
    {
        return this.tag == null;
    }

    @Override
    public boolean fuzzyEquals(FuzzyLockKey other)
    {
        if (other == this)
        {
            return true;
        }

        if (other instanceof NBTLockKey)
        {
            return similarNBT(getTag(), ((NBTLockKey) other).getTag());
        }

        return false;
    }
}