package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.entity.NarakaStormEntity;
import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.PlayerDeltaMovePacket;
import com.lalaalal.yummy.tags.YummyTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NarakaStormSkill extends TickableSkill {
    public static final String NAME = "naraka_storm";
    private final DescentAndFallMeteorSkill nextSkill;

    public NarakaStormSkill(PathfinderMob usingEntity, int cooldown, @Nullable DescentAndFallMeteorSkill nextSkill) {
        super(usingEntity, cooldown, 20, 10);
        this.nextSkill = nextSkill;
    }

    @Override
    public String getBaseName() {
        return NAME;
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
            pullEntities(level, usingEntity);
        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick % 4 == 0)
            wave(level, usingEntity, false);

        if (tick == tickDuration)
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);

        return super.tick(tick);
    }

    public static void pullEntities(Level level, PathfinderMob usingEntity) {
        AABB area = usingEntity.getBoundingBox().inflate(15);
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
        for (LivingEntity entity : entities) {
            if (usingEntity.distanceToSqr(entity) < 4 * 4 || entity.getType().is(YummyTags.HEROBRINE))
                continue;
            Vec3 deltaMovement = usingEntity.position().add(entity.position().reverse()).scale(0.1);
            entity.setDeltaMovement(entity.getDeltaMovement().add(deltaMovement));
            if (entity instanceof ServerPlayer serverPlayer)
                YummyMessages.sendToPlayer(new PlayerDeltaMovePacket(deltaMovement), serverPlayer);
        }
    }

    public static void wave(Level level, PathfinderMob usingEntity, boolean corrupted) {
        NarakaStormEntity narakaStormEntity = new NarakaStormEntity(level, 0, 4, usingEntity, corrupted);
        narakaStormEntity.setMaxRadius(15);
        narakaStormEntity.setSpeed(0.4);

        level.addFreshEntity(narakaStormEntity);
    }

    @Override
    public void interrupt() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
    }

    @Override
    public @Nullable TickableSkill getNextSkill() {
        return nextSkill;
    }
}
