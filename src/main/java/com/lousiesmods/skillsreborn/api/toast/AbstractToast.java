package com.lousiesmods.skillsreborn.api.toast;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractToast implements IToast
{
    private final String title;
    private String description;
    private long firstDrawTime;
    private boolean drawn;
    protected int x = 8, y = 8;
    protected long displayTime = 5000L;

    public AbstractToast(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

    @Override
    public Visibility func_230444_a_(MatrixStack matrixStack, ToastGui toastGui, long l)
    {
        if (!this.drawn)
        {
            this.drawn = true;
            this.firstDrawTime = l;
        }

        Minecraft mc = toastGui.getMinecraft();

        mc.getTextureManager().bindTexture(TEXTURE_TOASTS);
        GlStateManager.color4f(1.0F,1.0F, 1.0F, 1.0F);
        boolean hasImage = hasImage();
        int xDif = hasImage ? 0 : this.x;
        toastGui.blit(matrixStack, 0, 0, 0, 32, 160, 32);
        mc.fontRenderer.drawString(matrixStack, getTitle(), 30 - xDif, 7, -11534256);
        mc.fontRenderer.drawString(matrixStack, getDescription(), 30 - xDif, 18, -16777216);

        if (hasImage)
        {
            renderImage(toastGui);
        }

        return l - this.firstDrawTime >= displayTime ? Visibility.HIDE : Visibility.SHOW;
    }

    protected void bindImage(ToastGui guiToast, ResourceLocation sprite)
    {
        guiToast.getMinecraft().textureManager.bindTexture(sprite);
        GlStateManager.color4f(1.0F,1.0F, 1.0F, 1.0F);
    }

    protected abstract void renderImage(ToastGui guiToast);

    protected abstract boolean hasImage();

    public String getTitle()
    {
        return this.title;
    }

    public String getDescription()
    {
        return this.description;
    }
}