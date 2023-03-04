package com.lalaalal.yummy;

import com.lalaalal.yummy.networking.ModMessages;
import com.lalaalal.yummy.networking.packet.HitResultPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER;

public class YummyBoneMealItem extends BoneMealItem {
    private long lastUsedTime = 0;

    public YummyBoneMealItem() {
        super(new Properties().tab(CreativeModeTab.TAB_MISC));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();

        if (!checkUsingInterval() || !level.isClientSide() || player == null)
            return super.useOn(context);

        HitResult hitResult = Minecraft.getInstance().hitResult;
        if (hitResult == null)
            return InteractionResult.FAIL;
        double x = hitResult.getLocation().x;
        double y = hitResult.getLocation().y;
        double z = hitResult.getLocation().z;

        ModMessages.sendToServer(new HitResultPacket(x, y, z));

        BlockPos blockPos = new BlockPos(x, y, z);
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.is(BlockTags.SMALL_FLOWERS)) {
            addBoneMealParticle(level, x, y, z);
            level.playSound(player, blockPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 1);
            player.swing(player.getUsedItemHand(), true);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    private boolean checkUsingInterval() {
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - lastUsedTime;
        lastUsedTime = currentTime;

        return diff > 50;
    }

    private void addBoneMealParticle(Level level, double x, double y, double z) {
        Random random = new Random();

        for (int i = 0; i < 7; i++) {
            double particleX = x + random.nextDouble(-0.5, 0.5);
            double particleY = y + random.nextDouble(-0.5, 0.5);
            double particleZ = z + random.nextDouble(-0.5, 0.5);
            double speed = random.nextDouble(1.0, 1.7);

            level.addParticle(HAPPY_VILLAGER, particleX, particleY, particleZ, 0, speed, 0);
        }
    }
}
