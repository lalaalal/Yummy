package com.lalaalal.yummy.screen;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.world.inventory.AlembicMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlembicScreen extends AbstractContainerScreen<AlembicMenu> {
    private static final ResourceLocation TEXTURE_LOCATION
            = new ResourceLocation(YummyMod.MOD_ID, "textures/gui/alembic.png");

    public AlembicScreen(AlembicMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);

        renderDistillProgressArrow(poseStack, x, y);
        renderProgressArrow(poseStack, x, y);
    }

    private void renderDistillProgressArrow(PoseStack poseStack, int x, int y) {
        if (menu.isDistilling()) {
            blit(poseStack, x + 56, y + 65 - menu.getScaledDistillProgress(), 176, 33 - menu.getScaledDistillProgress(), 12, menu.getScaledDistillProgress() + 1);
            blit(poseStack, x + 54, y + 67, 176, 0, 18 - menu.getScaledFuelDistillProgress(), 4);
        }
    }

    private void renderProgressArrow(PoseStack poseStack, int x, int y) {
        blit(poseStack, x + 79, y + 35, 176, 33, menu.getScaledProgress() + 1, 16);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        renderTooltip(poseStack, mouseX, mouseY);
    }
}
