package com.lalaalal.yummy.networking.packet;

import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.entity.YummyEntityRegister;
import com.lalaalal.yummy.sound.YummySoundRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;

public class SpawnHerobrinePacket extends PositionPacket {
    public SpawnHerobrinePacket(BlockPos blockPos) {
        super(blockPos);
    }

    public SpawnHerobrinePacket(double x, double y, double z) {
        super(x, y, z);
    }

    public SpawnHerobrinePacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    protected void handleWork(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;

        ServerLevel level = player.getLevel();
        if (!Herobrine.canSummonHerobrine(level, getBlockPos()))
            return;

        BlockPos blockPos = new BlockPos(x, y, z);
        Herobrine.destroySpawnStructure(level, blockPos);
        level.playSound(null, blockPos, YummySoundRegister.HEROBRINE_SUMMON.get(), SoundSource.HOSTILE, 1, 1);
        YummyEntityRegister.HEROBRINE.get().spawn(level, null, null, null, blockPos, MobSpawnType.STRUCTURE, true, false);
    }
}
