package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.entity.FloatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

public class RushSkill extends TickableSkill {
    public static final float MIN_ATTACK_REACH = 5;
    public static final float ATTACK_REACH = 10;
    public static final Vec3 FLOATING_BLOCK_VELOCITY = new Vec3(0, 0.4, 0);
    private static final double MOVE_DISTANCE = 7;
    private Vec3 viewVector = Vec3.ZERO;
    private Vec3 targetPos = Vec3.ZERO;
    private Vec3 moveVectorReverse = Vec3.ZERO;
    private final Set<BlockPos> floatedBlocks = new HashSet<>();

    public RushSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 20, 10);
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
        if (target == null)
            return true;
        if (tick < 11) {
            usingEntity.lookAt(target, 0, 0);
            viewVector = usingEntity.getViewVector(2).multiply(1, 0, 1);
        }
        if (12 <= tick && tick <= 17)
            run();

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick == 0)
            rush();
        floatBlocks(tick);

        if (tick == tickDuration)
            floatedBlocks.clear();

        return super.tick(tick);
    }

    private void run() {
        usingEntity.move(MoverType.SELF, viewVector.scale(0.7));
    }

    private void rush() {
        LivingEntity target = usingEntity.getTarget();
        if (target == null)
            return;

        Vec3 moveOffset = viewVector.scale(MOVE_DISTANCE);
        targetPos = new Vec3(usingEntity.getX(), usingEntity.getY(), usingEntity.getZ()).add(moveOffset);
        moveVectorReverse = moveOffset.reverse();
        BlockPos sturdyBlockPos = YummyUtil.findHorizonPos(new BlockPos(targetPos), level);
        usingEntity.moveTo(targetPos.x, sturdyBlockPos.getY(), targetPos.z);
        usingEntity.setDeltaMovement(Vec3.ZERO);
        target.hurt(new EntityDamageSource("rush", usingEntity), 1);
        target.knockback(6, -viewVector.x, -viewVector.z);
        HerobrineMark.overlapMark(target, usingEntity);
    }

    private void floatBlocks(int tick) {
        double scale = MOVE_DISTANCE * 0.3 * (tickDuration - tick) / tickDuration;
        Vec3 offset1 = YummyUtil.calcXZRotation(moveVectorReverse, Math.PI / 12, scale);
        Vec3 offset2 = YummyUtil.calcXZRotation(moveVectorReverse, Math.PI / -12, scale);

        floatBlock(new BlockPos(targetPos.add(offset1)));
        floatBlock(new BlockPos(targetPos.add(offset2)));
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
