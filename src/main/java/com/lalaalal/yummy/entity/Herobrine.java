package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.entity.goal.FollowTargetGoal;
import com.lalaalal.yummy.entity.goal.TickableSkillUseGoal;
import com.lalaalal.yummy.entity.skill.*;
import com.lalaalal.yummy.misc.PhaseManager;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.ToggleHerobrineMusicPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.Map;

public class Herobrine extends CameraShakingEntity implements IAnimatable, SkillUsable, Enemy {
    private static final EntityDataAccessor<String> DATA_USING_SKILL_NAME = SynchedEntityData.defineId(Herobrine.class, EntityDataSerializers.STRING);
    private static final float[] PHASE_HEALTHS = {600, 60, 6};
    private static final BossEvent.BossBarColor[] PHASE_COLORS = {BossEvent.BossBarColor.BLUE, BossEvent.BossBarColor.YELLOW, BossEvent.BossBarColor.RED};
    private final Map<TickableSkill, String> skillNames = new HashMap<>();
    private final Map<String, TickableSkill> skills = new HashMap<>();
    private final TickableSkillUseGoal<Herobrine> skillUseGoal = new TickableSkillUseGoal<>(this);

    private final AnimationFactory animationFactory = GeckoLibUtil.createFactory(this);
    private final PhaseManager phaseManager = new PhaseManager(PHASE_HEALTHS, PHASE_COLORS, this);
    private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS))
            .setDarkenScreen(true)
            .setPlayBossMusic(true);

    private int hurtAnimationTick = 0;
    private int invulnerableTick = 30;

    public static boolean canSummonHerobrine(Level level, BlockPos headPos) {
        Block soulSandBlock = level.getBlockState(headPos).getBlock();
        Block netherBlock = level.getBlockState(headPos.below(1)).getBlock();
        Block goldBlock1 = level.getBlockState(headPos.below(2)).getBlock();
        Block goldBlock2 = level.getBlockState(headPos.below(3)).getBlock();

        return soulSandBlock == Blocks.SOUL_SAND
                && netherBlock == Blocks.CHISELED_NETHER_BRICKS
                && goldBlock1 == Blocks.GOLD_BLOCK
                && goldBlock2 == Blocks.GOLD_BLOCK;
    }

    public static void destroySpawnStructure(ServerLevel level, BlockPos headPos) {
        level.destroyBlock(headPos, false);
        level.destroyBlock(headPos.below(1), false);
        level.destroyBlock(headPos.below(2), false);
        level.destroyBlock(headPos.below(3), false);
        EntityType.LIGHTNING_BOLT.spawn(level, null, null, headPos.below(3), MobSpawnType.TRIGGERED, true, true);
    }

    public static AttributeSupplier.Builder getHerobrineAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.MAX_HEALTH, 666)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.ATTACK_DAMAGE, 16)
                .add(Attributes.ATTACK_KNOCKBACK, 6)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.MOVEMENT_SPEED, 0.20);
    }

    protected Herobrine(EntityType<? extends Herobrine> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        this.xpReward = 666;

        phaseManager.addPhaseChangeListener(this::changePhase);
        phaseManager.addPhaseChangeListener(this::enterPhase2, 2);
        registerSkills();
        setPersistenceRequired();
    }

    @Override
    public void setUsingSkill(@Nullable TickableSkill skill) {
        if (skill == null) {
            this.entityData.set(DATA_USING_SKILL_NAME, "none");
            return;
        }
        String name = skillNames.get(skill);
        this.entityData.set(DATA_USING_SKILL_NAME, name);
    }

    @Override
    public String getUsingSkillName() {
        return this.entityData.get(DATA_USING_SKILL_NAME);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_USING_SKILL_NAME, "none");
    }

    protected void registerSkills() {
        this.goalSelector.addGoal(1, skillUseGoal);
        registerSkill(new NarakaWaveSkill(this, 20 * 15), "naraka_wave");
        registerSkill(new ThrowNarakaFireballSkill(this, 20 * 6), "throw_naraka_fireball");
        registerSkill(new DescentAndFallMeteorSkill(this, 20 * 12), "descent_fall_meteor");
        registerSkill(new ExplosionMagicSkill(this, 20 * 30), "explosion_magic");
        registerSkill(new RushSkill(this, 20 * 10), "rush");
    }

    public void registerSkill(TickableSkill skill, String name) {
        skills.put(name, skill);
        skillNames.put(skill, name);
        skillUseGoal.addSkill(skill);
    }

    private void changePhase(int phase) {
        invulnerableTick = 30;
        setInvulnerable(true);
        LevelChunk levelChunk = level.getChunkAt(getOnPos());
        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(true, phase), levelChunk);
        setHealth(phaseManager.getActualCurrentPhaseMaxHealth());
    }

    private void enterPhase2() {
        AttributeInstance attributeInstance = getAttribute(Attributes.ARMOR);
        if (attributeInstance != null)
            attributeInstance.setBaseValue(66);
        if (skills.get("descent_fall_meteor") instanceof DescentAndFallMeteorSkill descentAndFallMeteorSkill)
            descentAndFallMeteorSkill.setMeteorMark(true);
    }

    public int getPhase() {
        return phaseManager.getCurrentPhase();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        String skillName = getUsingSkillName();
        if (!skillName.equals("none")) {
            hurtAnimationTick = 0;
            String animationName = String.format("animation.herobrine.%s", skillName);
            event.getController().setAnimation(new AnimationBuilder().addAnimation(animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (hurtAnimationTick > 0) {
            hurtAnimationTick -= 1;
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.herobrine.hit", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if (!event.isMoving())
            return PlayState.STOP;

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.herobrine.walk", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 5, 1));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, false));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.isBypassInvul())
            return super.hurt(source, amount);

        hurtAnimationTick += 37;
        if (invulnerableTick > 0)
            return true;

        return super.hurt(source, amount);
    }

    @Override
    protected void customServerAiStep() {
        if (invulnerableTick > 0) {
            invulnerableTick -= 1;
        } else {
            setInvulnerable(false);
        }

        phaseManager.updatePhase(bossEvent);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        bossEvent.addPlayer(serverPlayer);
        phaseManager.updatePhase(bossEvent);
        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(true, getPhase()), serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        bossEvent.removePlayer(serverPlayer);

        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(false), serverPlayer);
    }
}
