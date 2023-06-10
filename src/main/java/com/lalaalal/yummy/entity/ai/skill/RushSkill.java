package com.lalaalal.yummy.entity.ai.skill;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.entity.FloatingBlockEntity;
import com.lalaalal.yummy.world.damagesource.YummyDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

public class RushSkill extends TickableSkill {
    public static final String NAME = "rush";
    public static final float MIN_ATTACK_REACH = 5;
    public static final float ATTACK_REACH = 14;
    public static final Vec3 FLOATING_BLOCK_VELOCITY = new Vec3(0, 0.4, 0);
    private static final double MOVE_DISTANCE = 14;
    private static final int RUSH_DURATION = 8;
    private static final int FLOATING_DURATION = 12;
    private static final int START_RUN_TIME = 15;
    private Vec3 viewVector = Vec3.ZERO;
    private Vec3 targetPos = Vec3.ZERO;
    private Vec3 rushStartPos = Vec3.ZERO;
    private Vec3 moveVector = Vec3.ZERO;
    private float xRot = 0;
    private float yRot = 0;
    private boolean pushed = false;
    private final Set<BlockPos> floatedBlocks = new HashSet<>();

    public RushSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 20, 15);
    }

    @Override
    public String getBaseName() {
        return NAME;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = usingEntity.getTarget();
        if (target == null)
            return false;
        double distanceSquare = usingEntity.distanceToSqr(target);

        return distanceSquare > MIN_ATTACK_REACH * MIN_ATTACK_REACH && distanceSquare < ATTACK_REACH * ATTACK_REACH;
    }

    @Override
    public boolean animationTick(int tick) {
        LivingEntity target = usingEntity.getTarget();
        if (tick == 0)
            pushed = false;
        if (target == null)
            return true;
        if (tick < START_RUN_TIME) {
            usingEntity.lookAt(target, 360, 360);
            viewVector = usingEntity.getViewVector(0);
            xRot = usingEntity.getXRot();
            yRot = usingEntity.getYRot();
        }
        if (START_RUN_TIME <= tick)
            run();

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick < FLOATING_DURATION)
            floatBlocks(tick);
        if (tick > RUSH_DURATION)
            return super.tick(tick);

        if (tick == 0) {
            moveVector = viewVector.scale(MOVE_DISTANCE).multiply(1, 0, 1);
            rushStartPos = new Vec3(usingEntity.getX(), usingEntity.getY(), usingEntity.getZ());
            targetPos = rushStartPos.add(moveVector);
        }

        rush(tick);

        if (tick == RUSH_DURATION) {
            floatedBlocks.clear();
        }

        return false;
    }

    @Override
    public void interrupt() {

    }

    private void run() {
        usingEntity.setXRot(xRot);
        usingEntity.setYRot(yRot);
        usingEntity.move(MoverType.SELF, viewVector.scale(1));
    }

    private void rush(int tick) {
        Vec3 stepMovement = moveVector.scale(tick / (double) RUSH_DURATION);
        Vec3 stepPos = rushStartPos.add(stepMovement);
        BlockPos sturdyBlockPos = YummyUtil.findHorizonPos(YummyUtil.blockPos(targetPos), level);
        usingEntity.moveTo(stepPos.x, sturdyBlockPos.getY(), stepPos.z);
        usingEntity.setDeltaMovement(Vec3.ZERO);
        LivingEntity target = usingEntity.getTarget();
        if (target == null)
            return;

        if (!pushed && usingEntity.distanceToSqr(target) < 2) {
            target.hurt(YummyDamageSources.simple(level, YummyMod.MOD_ID + ".herobrine.rush", usingEntity), 1);
            target.knockback(6, -viewVector.x, -viewVector.z);
            HerobrineMark.overlapMark(target, usingEntity);
            pushed = true;
        }
    }

    private void floatBlocks(int tick) {
        double scale = MOVE_DISTANCE * 0.1 * (FLOATING_DURATION - tick) / FLOATING_DURATION;
        for (int i = 1; i <= 3; i++) {
            Vec3 offset1 = YummyUtil.calcXZRotation(moveVector.reverse(), Math.PI / (12 * i), scale);
            Vec3 offset2 = YummyUtil.calcXZRotation(moveVector.reverse(), Math.PI / (-12 * i), scale);

            floatBlock(YummyUtil.blockPos(targetPos.add(offset1)));
            floatBlock(YummyUtil.blockPos(targetPos.add(offset2)));
        }
    }

    private void floatBlock(BlockPos pos) {
        if (floatedBlocks.contains(pos))
            return;
        floatedBlocks.add(pos);
        BlockPos floatingBlockPos = YummyUtil.findHorizonPos(pos, level).below();
        BlockState state = Blocks.MAGMA_BLOCK.defaultBlockState();
        FloatingBlockEntity.floatBlock(level, new BlockPos(floatingBlockPos), FLOATING_BLOCK_VELOCITY, FloatingBlockEntity.DEFAULT_ACCELERATION, state);
    }
}
