package com.lalaalal.yummy.screen;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.world.inventory.SoulCraftingMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulCraftingScreen extends AbstractContainerScreen<SoulCraftingMenu> {
    private static final ResourceLocation TEXTURE_LOCATION
            = new ResourceLocation(YummyMod.MOD_ID, "textures/gui/soul_crafter.png");

    public SoulCraftingScreen(SoulCraftingMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE_LOCATION, x, y, 0, 0, imageWidth, imageHeight);

        renderDistillProgressArrow(guiGraphics, x, y);
        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderDistillProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isDistilling()) {
            guiGraphics.blit(TEXTURE_LOCATION, x + 56, y + 65 - menu.getScaledDistillProgress(), 176, 33 - menu.getScaledDistillProgress(), 12, menu.getScaledDistillProgress() + 1);
            guiGraphics.blit(TEXTURE_LOCATION, x + 54, y + 67, 176, 0, 18 - menu.getScaledFuelDistillProgress(), 4);
        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(TEXTURE_LOCATION, x + 79, y + 35, 176, 33, menu.getScaledProgress() + 1, 16);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
