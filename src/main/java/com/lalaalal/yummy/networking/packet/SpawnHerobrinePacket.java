package com.lalaalal.yummy.networking.packet;

import com.lalaalal.yummy.entity.HerobrineEntity;
import com.lalaalal.yummy.entity.YummyEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
        if (!HerobrineEntity.canSummonHerobrine(level, getBlockPos()))
            return;

        BlockPos blockPos = new BlockPos(x, y, z);
        HerobrineEntity.destroySpawnStructure(level, blockPos);
        YummyEntityRegister.HEROBRINE.get().spawn(level, null, null, null, blockPos, MobSpawnType.STRUCTURE, true, false);
    }
}
