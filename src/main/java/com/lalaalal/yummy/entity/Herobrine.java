package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import com.lalaalal.yummy.entity.goal.FollowTargetGoal;
import com.lalaalal.yummy.entity.skill.*;
import com.lalaalal.yummy.misc.PhaseManager;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.ToggleHerobrineMusicPacket;
import com.lalaalal.yummy.tags.YummyTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashSet;
import java.util.Set;

public class Herobrine extends AbstractHerobrine {
    public static final int DEATH_TICK_DURATION = 100;
    private static final int HURT_ANIMATION_DURATION = 37;
    private static final int CONSUME_MARK_HEAL = 66;
    private static final float[] PHASE_HEALTHS = {600, 60, 6};
    private static final float[] HEALTH_CHANGE_CHECK = {12, 18, 24};
    private static final BossEvent.BossBarColor[] PHASE_COLORS = {BossEvent.BossBarColor.BLUE, BossEvent.BossBarColor.YELLOW, BossEvent.BossBarColor.RED};

    private final AnimationFactory animationFactory = GeckoLibUtil.createFactory(this, false);
    private final PhaseManager phaseManager = new PhaseManager(PHASE_HEALTHS, PHASE_COLORS, this);
    private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS))
            .setDarkenScreen(true)
            .setPlayBossMusic(true);

    private BlockPos structurePos;
    private DamageSource deathDamageSource;
    private final SummonShadowHerobrineSkill summonShadowHerobrineSkill = new SummonShadowHerobrineSkill(this, 20 * 60);
    private int hurtAnimationTick = 0;
    private int invulnerableTick = 30;
    private int deathTick = 0;
    private int corruptedWaveStack = 0;
    private boolean preserveHealth = false;

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
        super(entityType, level, false);
        this.noCulling = true;
        this.xpReward = 666;

        phaseManager.addPhaseChangeListener(this::changePhase);
        phaseManager.addPhaseChangeListener(this::enterPhase2, 2);
        phaseManager.addPhaseChangeListener(this::enterPhase3, 3);
        phaseManager.addHealthChangeListener(this::updateShadowSpeed);

        setPersistenceRequired();
    }

    public Herobrine(Level level, BlockPos spawnPos, BlockPos structurePos) {
        this(YummyEntities.HEROBRINE.get(), level);
        this.structurePos = structurePos;
        setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        phaseManager.updateBossBarColorOnly(bossEvent);
        structurePos = YummyUtil.readBlockPos(compoundTag, "StructurePos");
        preserveHealth = true;
        if (getPhase() >= 2)
            enterPhase2();
        if (getPhase() == 3)
            enterPhase3();

        preserveHealth = false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (structurePos != null)
            YummyUtil.saveBlockPos(compoundTag, "StructurePos", structurePos);
    }

    public void consumeMark() {
        final float currentMaxHealth = phaseManager.getActualCurrentPhaseMaxHealth();
        setHealth(Math.min(getHealth() + CONSUME_MARK_HEAL, currentMaxHealth));
    }

    public int getDeathTick() {
        return deathTick;
    }

    public void increaseCorruptedWaveStack() {
        corruptedWaveStack += 1;
    }

    public boolean canUseCorruptedWave() {
        return getPhase() == 3 && corruptedWaveStack >= 6;
    }

    public void resetCorruptedWaveStack() {
        corruptedWaveStack = 0;
    }

    private void changePhase(int from, int to) {
        if (from > to || preserveHealth)
            return;
        invulnerableTick = 30;
        setInvulnerable(true);
        LevelChunk levelChunk = level.getChunkAt(getOnPos());
        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(true, to), levelChunk);
        setHealth(phaseManager.getActualCurrentPhaseMaxHealth());
    }

    private void enterPhase2() {
        AttributeInstance attributeInstance = getAttribute(Attributes.ARMOR);
        if (attributeInstance != null)
            attributeInstance.setBaseValue(66);
        if (getSkill(DescentAndFallMeteorSkill.NAME) instanceof DescentAndFallMeteorSkill descentAndFallMeteorSkill) {
            descentAndFallMeteorSkill.setMeteorMark(true);
            registerSkill(new NarakaStormSkill(this, 20 * 40, descentAndFallMeteorSkill));
        }
        interrupt();
        registerSkill(summonShadowHerobrineSkill, true);
        registerSkill(new FractureRushSkill(this, 20 * 5));
        removeSkill(RushSkill.NAME);
    }

    private void enterPhase3() {
        if (structurePos != null)
            moveTo(structurePos, getYRot(), getXRot());

        for (ShadowHerobrine shadowHerobrine : summonShadowHerobrineSkill.getShadowHerobrines()) {
            shadowHerobrine.changeSpeed(ShadowHerobrine.DEFAULT_MOVEMENT_SPEED * 2);
            shadowHerobrine.registerSkill(new FractureRushSkill(shadowHerobrine, 20 * 5));
        }

        YummyAttributeModifiers.addPermanentModifier(this, YummyAttributeModifiers.PREVENT_MOVING);
        YummyAttributeModifiers.addPermanentModifier(this, YummyAttributeModifiers.IGNORE_KNOCKBACK);

        removeSkill(NarakaWaveSkill.NAME);
        removeSkill(ThrowNarakaFireballSkill.NAME);
        removeSkill(DescentAndFallMeteorSkill.NAME);
        removeSkill(ExplosionMagicSkill.NAME);
        removeSkill(RushSkill.NAME);
        removeSkill(NarakaStormSkill.NAME);
        removeSkill(FractureRushSkill.NAME);

        registerSkill(new CorruptedWaveSkill(this, 0));
    }

    private void updateShadowSpeed(float prevHealth, float currentHealth) {
        for (int i = 0; i < HEALTH_CHANGE_CHECK.length; i++) {
            float checkHealth = HEALTH_CHANGE_CHECK[i];
            if (currentHealth <= checkHealth && checkHealth < prevHealth) {
                for (ShadowHerobrine shadowHerobrine : summonShadowHerobrineSkill.getShadowHerobrines())
                    shadowHerobrine.changeSpeed(ShadowHerobrine.DEFAULT_MOVEMENT_SPEED * Math.pow(1.2, i + 1));
            }
        }
    }

    public double calcCurrentShadowSpeed() {
        if (getPhase() == 3)
            return ShadowHerobrine.DEFAULT_MOVEMENT_SPEED * 2;

        float currentHealth = getHealth();
        for (int i = 0; i < HEALTH_CHANGE_CHECK.length; i++) {
            float checkHealth = HEALTH_CHANGE_CHECK[i];
            if (currentHealth <= checkHealth) {
                return ShadowHerobrine.DEFAULT_MOVEMENT_SPEED * Math.pow(1.2, i + 1);
            }
        }

        return ShadowHerobrine.DEFAULT_MOVEMENT_SPEED;
    }

    public int getPhase() {
        return phaseManager.getCurrentPhase();
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        GroundPathNavigation pathNavigation = new GroundPathNavigation(this, level);
        pathNavigation.setCanOpenDoors(true);
        pathNavigation.setCanFloat(true);
        pathNavigation.setAvoidSun(false);
        return pathNavigation;
    }

    @Override
    protected void registerSkills() {
        registerSkill(new NarakaWaveSkill(this, 20 * 15));
        registerSkill(new ThrowNarakaFireballSkill(this, 20 * 6));
        registerSkill(new DescentAndFallMeteorSkill(this, 20 * 12));
        registerSkill(new ExplosionMagicSkill(this, 20 * 30));
        registerSkill(new RushSkill(this, 20 * 10));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        String skillName = getUsingSkillName();
        if (!skillName.equals(SKILL_NONE)) {
            hurtAnimationTick = 0;
            String animationName = "animation.herobrine." + skillName;
            event.getController().setAnimation(new AnimationBuilder().addAnimation(animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (getPhase() == 3) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.herobrine.phase_3_basic", ILoopType.EDefaultLoopTypes.LOOP));
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
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, ShadowHerobrine.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, (livingEntity) -> !livingEntity.getType().is(YummyTags.HEROBRINE)));
    }

    private float currentPhaseMaxHurtDamage() {
        return switch (getPhase()) {
            case 1 -> 20.0f;
            case 2 -> 6f;
            default -> 1f;
        };
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.equals(DamageSource.IN_WALL))
            destroyNearbyBlocks();
        if (source.isBypassInvul())
            return super.hurt(source, amount);

        if (getPhase() == 3) {
            Entity entity = source.getEntity();
            if (entity != null && entity.getType().is(YummyTags.HEROBRINE))
                return super.hurt(source.bypassArmor(), 1);
            return false;
        }

        if (deathTick > 0)
            return false;

        hurtAnimationTick = (hurtAnimationTick + HURT_ANIMATION_DURATION) % (HURT_ANIMATION_DURATION * 2);
        if (invulnerableTick > 0)
            return false;

        return super.hurt(source, Math.min(amount, currentPhaseMaxHurtDamage()));
    }

    private void destroyNearbyBlocks() {
        if (level.isClientSide)
            return;

        BlockPos basePos = getOnPos().west().north();
        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                for (int y = 0; y < 3; y++) {
                    BlockPos blockPos = basePos.east(x).south(z).above(y);
                    level.destroyBlock(blockPos, false);
                }
            }
        }
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
    public void die(DamageSource damageSource) {
        this.deathDamageSource = damageSource;
    }

    @Override
    protected void tickDeath() {
        if (deathTick == 0) {
            setDeltaMovement(0, 0.7, 0);
            setNoGravity(true);
        }
        move(MoverType.SELF, getDeltaMovement());
        Vec3 velocity = getDeltaMovement().add(0, -0.04, 0);
        if (velocity.y > 0) {
            setDeltaMovement(velocity);
        } else {
            if (!level.isClientSide && deathTick % 4 == 0)
                destroyBlocks(deathTick / 4);
        }

        if (deathTick == DEATH_TICK_DURATION) {
            this.remove(Entity.RemovalReason.KILLED);
            this.gameEvent(GameEvent.ENTITY_DIE);
            if (deathDamageSource != null && !level.isClientSide) {
                dead = true;
                dropAllDeathLoot(deathDamageSource);
                ExperienceOrb.award((ServerLevel) this.level, this.position(), xpReward);
            }
        }
        deathTick += 1;
    }

    private void destroyBlocks(int radius) {
        Set<BlockPos> destroyedPos = new HashSet<>();
        for (double y_t = 0; y_t < 180; y_t += 0.25) {
            double currentRadius = Math.sin(y_t * Math.PI / 180) * radius;
            double y = Math.floor(getY() + Math.cos(y_t * Math.PI / 180) * radius);

            for (double xz_t = 0; xz_t < 360; xz_t += 0.25) {
                double x = getX() + Math.cos(xz_t * Math.PI / 180) * currentRadius;
                double z = getZ() + Math.sin(xz_t * Math.PI / 180) * currentRadius;

                BlockPos pos = new BlockPos(x, y, z);
                if (destroyedPos.contains(pos) || level.getBlockState(pos).is(Blocks.BEDROCK))
                    continue;
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                destroyedPos.add(pos);
            }
        }

    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        bossEvent.addPlayer(serverPlayer);
        phaseManager.updatePhase(bossEvent);
        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(true, getPhase()), serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        bossEvent.removePlayer(serverPlayer);
        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(false), serverPlayer);
    }
}
