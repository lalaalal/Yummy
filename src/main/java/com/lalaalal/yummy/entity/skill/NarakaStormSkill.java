package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.entity.NarakaStormEntity;
import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.PlayerDeltaMovePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NarakaStormSkill extends TickableSkill {
    private final DescentAndFallMeteorSkill nextSkill;

    public NarakaStormSkill(PathfinderMob usingEntity, int cooldown, @Nullable DescentAndFallMeteorSkill nextSkill) {
        super(usingEntity, cooldown, 20, 10);
        this.nextSkill = nextSkill;
    }

    @Override
    public String getBaseName() {
        return "naraka_storm";
    }

    @Override
    public boolean canUse() {
        return usingEntity.getTarget() != null;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == 0)
            YummyAttributeModifiers.addTransientModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
        if (tick < animationDuration)
            pullEntities();
        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick % 4 == 0)
            wave();

        if (tick == tickDuration)
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);

        return super.tick(tick);
    }

    private void pullEntities() {
        AABB area = usingEntity.getBoundingBox().inflate(15);
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
        for (LivingEntity entity : entities) {
            if (usingEntity.distanceToSqr(entity) < 4 * 4)
                continue;
            Vec3 deltaMovement = usingEntity.position().add(entity.position().reverse()).scale(0.1);
            entity.setDeltaMovement(entity.getDeltaMovement().add(deltaMovement));
            if (entity instanceof ServerPlayer serverPlayer)
                YummyMessages.sendToPlayer(new PlayerDeltaMovePacket(deltaMovement), serverPlayer);
        }
    }

    private void wave() {
        NarakaStormEntity narakaStormEntity = new NarakaStormEntity(level, 0, 4, usingEntity);
        narakaStormEntity.setMaxRadius(15);
        narakaStormEntity.setSpeed(0.4);

        level.addFreshEntity(narakaStormEntity);
    }

    @Override
    public void interrupted() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
    }

    @Override
    public @Nullable TickableSkill getNextSkill() {
        return nextSkill;
    }
}
