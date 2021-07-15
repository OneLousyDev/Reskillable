package com.lousiesmods.skillsreborn.api.data;

import com.lousiesmods.skillsreborn.api.requirement.RequirementCache;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class PlayerDataHandler 
{
    private static final String DATA_TAG = "SkillableData";
    private static HashMap<Integer, PlayerData> playerData = new HashMap<>();

    public static PlayerData get(PlayerEntity player) 
    {
        if (player == null) 
        {
            return null;
        }

        int key = getKey(player);
        
        if (!playerData.containsKey(key))
        {
            playerData.put(key, new PlayerData(player));
        }

        PlayerData data = playerData.get(key);
        
        if (data.playerWR.get() != player) 
        {
            CompoundNBT cmp = new CompoundNBT();
            data.saveToNBT(cmp);
            RequirementCache.removeCache(player.getUniqueID(), player.getEntityWorld().isRemote);
            playerData.remove(key);
            data = get(player);
            data.loadFromNBT(cmp);
        }

        return data;
    }

    public static void cleanup()
    {
        List<Integer> removals = new ArrayList<>();

        for (Entry<Integer, PlayerData> item : playerData.entrySet())
        {
            PlayerData d = item.getValue();
            if (d != null && d.playerWR.get() == null)
            {
                removals.add(item.getKey());
            }
        }

        removals.forEach(i -> playerData.remove(i));
    }

    private static int getKey(PlayerEntity player)
    {
        return player == null ? 0 : player.hashCode() << 1 + (player.getEntityWorld().isRemote ? 1 : 0);
    }

    public static CompoundNBT getDataCompoundForPlayer(PlayerEntity player)
    {
        CompoundNBT forgeData = player.getEntityData();

        if (!forgeData.contains(PlayerEntity.PERSISTED_NBT_TAG))
        {
            forgeData.put(PlayerEntity.PERSISTED_NBT_TAG, new CompoundNBT());
        }

        CompoundNBT persistentData = forgeData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);

        if (!persistentData.contains(DATA_TAG))
        {
            persistentData.put(DATA_TAG, new CompoundNBT());
        }

        return persistentData.getCompound(DATA_TAG);
    }

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onServerTick(TickEvent.ServerTickEvent event)
        {
            if (event.phase == TickEvent.Phase.END)
            {
                PlayerDataHandler.cleanup();
            }
        }

        @SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
        {
            PlayerData data = PlayerDataHandler.get(event.getPlayer());

            if (data != null)
            {
                data.sync();
                data.getRequirementCache().forceClear();
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event)
        {
            if (event.phase == TickEvent.Phase.END)
            {
                PlayerData data = PlayerDataHandler.get(event.player);

                if (data != null)
                {
                    data.tickPlayer(event);
                }
            }
        }

        @SubscribeEvent
        public static void onBlockDrops(HarvestDropsEvent event)
        {
            PlayerData data = PlayerDataHandler.get(event.getHarvester());

            if (data != null)
            {
                data.blockDrops(event);
            }
        }

        @SubscribeEvent
        public static void onGetBreakSpeed(BreakSpeed event)
        {
            PlayerData data = PlayerDataHandler.get(event.getPlayer());

            if (data != null)
            {
                data.breakSpeed(event);
            }
        }

        @SubscribeEvent
        public static void onMobDrops(LivingDropsEvent event)
        {
            if (event.getSource().getTrueSource() instanceof PlayerEntity)
            {
                PlayerData data = PlayerDataHandler.get((PlayerEntity) event.getSource().getTrueSource());

                if (data != null)
                {
                    data.mobDrops(event);
                }
            }
        }

        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event)
        {
            if (event.getEntity() instanceof PlayerEntity)
            {
                PlayerData data = PlayerDataHandler.get((PlayerEntity) event.getEntity());

                if (data != null)
                {
                    data.hurt(event);
                }
            }

            if (event.getSource().getTrueSource() instanceof PlayerEntity)
            {
                PlayerData data = PlayerDataHandler.get((PlayerEntity) event.getSource().getTrueSource());

                if (data != null)
                {
                    data.attackMob(event);
                }
            }
        }

        @SubscribeEvent
        public static void onRightClickBlock(RightClickBlock event)
        {
            PlayerData data = PlayerDataHandler.get(event.getPlayer());

            if (data != null)
            {
                data.rightClickBlock(event);
            }
        }

        @SubscribeEvent
        public static void onEnderTeleport(EnderTeleportEvent event)
        {
            if (event.getEntity() instanceof PlayerEntity)
            {
                PlayerData data = PlayerDataHandler.get((PlayerEntity) event.getEntity());

                if (data != null)
                {
                    data.enderTeleport(event);
                }
            }
        }

        @SubscribeEvent
        public static void onMobDeath(LivingDeathEvent event)
        {
            if (event.getSource().getTrueSource() instanceof PlayerEntity)
            {
                PlayerData data = PlayerDataHandler.get((PlayerEntity) event.getSource().getTrueSource());

                if (data != null)
                {
                    data.killMob(event);
                }
            }
        }
    }
}