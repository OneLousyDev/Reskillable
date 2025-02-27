package com.lousiesmods.skillsreborn.network;

import com.lousiesmods.skillsreborn.api.ReskillableRegistries;
import com.lousiesmods.skillsreborn.api.data.PlayerData;
import com.lousiesmods.skillsreborn.api.data.PlayerDataHandler;
import com.lousiesmods.skillsreborn.api.data.PlayerSkillInfo;
import com.lousiesmods.skillsreborn.api.event.UnlockUnlockableEvent;
import com.lousiesmods.skillsreborn.api.skill.Skill;
import com.lousiesmods.skillsreborn.api.unlockable.Unlockable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public class MessageUnlockUnlockable implements IMessage, IMessageHandler<MessageUnlockUnlockable, IMessage> {
    private ResourceLocation skill;
    private ResourceLocation unlockable;

    public MessageUnlockUnlockable() {
    }

    public MessageUnlockUnlockable(ResourceLocation skill, ResourceLocation unlockable) {
        this.skill = skill;
        this.unlockable = unlockable;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        skill = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
        unlockable = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, skill.toString());
        ByteBufUtils.writeUTF8String(buf, unlockable.toString());
    }

    @Override
    public IMessage onMessage(MessageUnlockUnlockable message, MessageContext ctx) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> handleMessage(message, ctx));
        return null;
    }

    public IMessage handleMessage(MessageUnlockUnlockable message, MessageContext context) {
        EntityPlayer player = context.getServerHandler().player;
        Skill skill = ReskillableRegistries.SKILLS.getValue(message.skill);
        Unlockable unlockable = Objects.requireNonNull(ReskillableRegistries.UNLOCKABLES.getValue(message.unlockable));
        PlayerData data = PlayerDataHandler.get(player);
        PlayerSkillInfo info = data.getSkillInfo(skill);

        if (!info.isUnlocked(unlockable) && info.getSkillPoints() >= unlockable.getCost() && data.matchStats(unlockable.getRequirements())
                && !MinecraftForge.EVENT_BUS.post(new UnlockUnlockableEvent.Pre(player, unlockable))) {
            info.unlock(unlockable, player);
            data.saveAndSync();
            MinecraftForge.EVENT_BUS.post(new UnlockUnlockableEvent.Post(player, unlockable));
        }
        return null;
    }
}