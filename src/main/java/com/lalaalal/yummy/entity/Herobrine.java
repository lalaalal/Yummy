package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.PollutedBlock;
import com.lalaalal.yummy.block.YummyBlockRegister;
import com.lalaalal.yummy.block.entity.PollutedBlockEntity;
import com.lalaalal.yummy.effect.YummyEffectRegister;
import com.lalaalal.yummy.entity.goal.SkillUseGoal;
import com.lalaalal.yummy.entity.skill.*;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.ToggleHerobrineMusicPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;

public class Herobrine extends Monster {
    private static final EntityDataAccessor<Integer> DATA_SKILL_USE_ID = SynchedEntityData.defineId(Herobrine.class, EntityDataSerializers.INT);
    private final ArrayList<BlockPos> blockPosList = new ArrayList<>();
    private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
    private final PhaseManager phaseManager = new PhaseManager();
    private int invulnerableTick = 0;
    private static final int INVULNERABLE_DURATION = 20 * 5;
    private BlockPos initialPos;

    public static boolean canSummonHerobrine(Level level, BlockPos headPos) {
        Block soulFireBlock = level.getBlockState(headPos.above()).getBlock();
        Block soulSandBlock = level.getBlockState(headPos).getBlock();
        Block netherBlock = level.getBlockState(headPos.below(1)).getBlock();
        Block goldBlock1 = level.getBlockState(headPos.below(2)).getBlock();
        Block goldBlock2 = level.getBlockState(headPos.below(3)).getBlock();

        return soulFireBlock == Blocks.SOUL_FIRE
                && soulSandBlock == Blocks.SOUL_SAND
                && netherBlock == Blocks.CHISELED_NETHER_BRICKS
                && goldBlock1 == Blocks.GOLD_BLOCK
                && goldBlock2 == Blocks.GOLD_BLOCK;
    }

    public static void polluteHerobrineAlter(Level level, BlockPos headPos) {
        level.setBlock(headPos.above(), YummyBlockRegister.PURIFIED_SOUL_FIRE_BLOCK.get().defaultBlockState(), 10);
        level.setBlock(headPos, YummyBlockRegister.DISPLAYING_POLLUTED_BLOCK.get()
                .defaultBlockState()
                .setValue(PollutedBlock.POWERED, true), 10);
        level.setBlock(headPos.below(), YummyBlockRegister.DISPLAYING_POLLUTED_BLOCK.get().defaultBlockState(), 10);
        level.setBlock(headPos.below(1), YummyBlockRegister.DISPLAYING_POLLUTED_BLOCK.get().defaultBlockState(), 10);
        level.setBlock(headPos.below(2), YummyBlockRegister.DISPLAYING_POLLUTED_BLOCK.get()
                .defaultBlockState()
                .setValue(PollutedBlock.CORRUPTED, true), 10);
        level.setBlock(headPos.below(3), YummyBlockRegister.DISPLAYING_POLLUTED_BLOCK.get()
                .defaultBlockState()
                .setValue(PollutedBlock.CORRUPTED, true), 10);
    }

    public static void destroySpawnStructure(Level level, BlockPos headPos) {
        level.destroyBlock(headPos, false);
        level.destroyBlock(headPos.below(1), false);
        level.destroyBlock(headPos.below(2), false);
        level.destroyBlock(headPos.below(3), false);
    }

    public static AttributeSupplier.Builder getHerobrineAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 666)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.ATTACK_DAMAGE, 16)
                .add(Attributes.ATTACK_KNOCKBACK, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2);
    }

    public Herobrine(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 666;
        this.entityData.define(DATA_SKILL_USE_ID, 0);
    }

    public void setInitialPos(BlockPos blockPos) {
        initialPos = blockPos;
    }

    public int getPhase() {
        return phaseManager.getCurrentPhase();
    }

    public void setArmPose(ArmPose armPose) {
        entityData.set(DATA_SKILL_USE_ID, armPose.getId());
    }

    public ArmPose getArmPose() {
        int armPoseID = entityData.get(DATA_SKILL_USE_ID);
        return ArmPose.byId(armPoseID);
    }

    private int[] blockPosToIntArray(BlockPos blockPos) {
        return new int[]{blockPos.getX(), blockPos.getY(), blockPos.getZ()};
    }

    private BlockPos blockPosFromIntArray(int[] array) {
        return new BlockPos(array[0], array[1], array[2]);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("numPollutedBlocks", blockPosList.size());
        for (int i = 0; i < blockPosList.size(); i++)
            tag.putIntArray("blockPosList" + i, blockPosToIntArray(blockPosList.get(i)));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        Level level = getLevel();
        int numPollutedBlocks = tag.getInt("numPollutedBlocks");
        for (int i = 0; i < numPollutedBlocks; i++) {
            int[] array = tag.getIntArray("blockPosList" + i);
            BlockPos blockPos = blockPosFromIntArray(array);
            blockPosList.add(blockPos);
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
                pollutedBlockEntity.setHerobrine(this);
        }
    }

    public boolean canSummonPollutedBlock() {
        int maxPollutedBlock = getPhase() != phaseManager.getMaxPhase() ? 6 : 10;

        return blockPosList.size() < maxPollutedBlock;
    }

    public void addPollutedBlock(PollutedBlockEntity pollutedBlockEntity) {
        blockPosList.add(pollutedBlockEntity.getBlockPos());
        pollutedBlockEntity.setHerobrine(this);
    }

    public void removePollutedBlock(PollutedBlockEntity pollutedBlockEntity) {
        blockPosList.remove(pollutedBlockEntity.getBlockPos());
    }

    @Override
    protected void customServerAiStep() {
        if (invulnerableTick < INVULNERABLE_DURATION) {
            invulnerableTick += 1;
        } else if (invulnerableTick == INVULNERABLE_DURATION) {
            setInvulnerable(false);
        }
        if (phaseManager.isPhaseChanged()) {
            invulnerableTick = 0;
            LevelChunk levelChunk = level.getChunkAt(getOnPos());
            YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(true, getPhase()), levelChunk);
            setInvulnerable(true);
            setHealth(phaseManager.getActualCurrentPhaseMaxHealth());
            YummyMod.LOGGER.debug("Setting health to " + phaseManager.getActualCurrentPhaseMaxHealth());

            if (getPhase() == phaseManager.getMaxPhase())
                enterPhase3();
        }
        phaseManager.updateBossProgressBar(bossEvent);
    }

    private void enterPhase3() {
        if (initialPos != null) {
            destroySpawnStructure(level, initialPos);
            moveTo(initialPos, 0, 0);
        }
        goalSelector.removeAllGoals();
        targetSelector.removeAllGoals();
        getNavigation().stop();

        setArmPose(ArmPose.RAISE_BOTH);
        this.goalSelector.addGoal(1, new SkillUseGoal(this, new KnockbackAndMarkSkill(this)));
        this.goalSelector.addGoal(2, new SkillUseGoal(this, new AddDigSlownessSkill(this)));
        this.goalSelector.addGoal(3, new SkillUseGoal(this, new SummonPollutedBlockSkill(this, 10)));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(1, new SkillUseGoal(this, new TeleportAndShootMeteorSkill(this)));
        this.goalSelector.addGoal(2, new SkillUseGoal(this, new ShootFireballSkill(this)));
        this.goalSelector.addGoal(3, new SkillUseGoal(this, new ExplosionSkill(this)));
        this.goalSelector.addGoal(2, new SkillUseGoal(this, new SummonPollutedBlockSkill(this)));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, false, false));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.isBypassInvul())
            return super.hurt(source, amount);
        if (random.nextInt(0, 10) == 5) {
            if (source.getEntity() instanceof LivingEntity livingEntity) {
                MobEffectInstance mobEffectInstance = new MobEffectInstance(YummyEffectRegister.STUN.get(), 60, 0);
                livingEntity.addEffect(mobEffectInstance);
            }
        }
        if (invulnerableTick < INVULNERABLE_DURATION)
            return true;

        int maxPhase = phaseManager.getMaxPhase();
        if (getPhase() != maxPhase && getHealth() - amount < 0) {
            super.hurt(source, 0.1f);
            setHealth(phaseManager.getPhaseMaxHealth(maxPhase));

            return true;
        }
        if (getPhase() == maxPhase) {
            if (source.isMagic())
                return super.hurt(source.bypassArmor(), 1f);
            return true;
        }

        return super.hurt(source, amount);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        bossEvent.addPlayer(serverPlayer);
        phaseManager.updateBossProgressBar(bossEvent);
        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(true, getPhase()), serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        bossEvent.removePlayer(serverPlayer);
        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(false, 1), serverPlayer);
        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(false, 2), serverPlayer);
        YummyMessages.sendToPlayer(new ToggleHerobrineMusicPacket(false, 3), serverPlayer);
    }

    private class PhaseManager {
        public static final float HEALTH_PHASE_1 = 600;
        public static final float HEALTH_PHASE_2 = 60;
        public static final float HEALTH_PHASE_3 = 6;

        private int prevPhase = 1;
        private final float[] phaseHealthArray = {HEALTH_PHASE_1, HEALTH_PHASE_2, HEALTH_PHASE_3};
        private final BossEvent.BossBarColor[] bossBarColors = {BossEvent.BossBarColor.BLUE, BossEvent.BossBarColor.YELLOW, BossEvent.BossBarColor.RED};

        public int getMaxPhase() {
            return phaseHealthArray.length;
        }

        public int getCurrentPhase() {
            float leftHealth = getMaxHealth();
            for (int phase = 0; phase < phaseHealthArray.length; phase++) {
                float phaseMinHealth = (leftHealth -= phaseHealthArray[phase]);
                if (getHealth() > phaseMinHealth)
                    return phase + 1;
            }
            return phaseHealthArray.length;
        }

        public float getPhaseMaxHealth(int phase) {
            return phaseHealthArray[phase - 1];
        }

        public float getCurrentPhaseHealth() {
            int phase = getCurrentPhase();
            float health = getHealth();
            for (int i = 0; i < phaseHealthArray.length - phase; i++) {
                int index = phaseHealthArray.length - i - 1;
                health -= phaseHealthArray[index];
            }

            return health;
        }

        public float getActualCurrentPhaseMaxHealth() {
            int phase = getCurrentPhase();
            float health = 0;
            for (int i = 0; i < phaseHealthArray.length - phase + 1; i++) {
                int index = phaseHealthArray.length - i - 1;
                health += phaseHealthArray[index];
            }

            return health;
        }

        private BossEvent.BossBarColor getPhaseColor(int phase) {
            return bossBarColors[phase - 1];
        }

        public boolean isPhaseChanged() {
            return prevPhase != getCurrentPhase();
        }

        public void updateBossProgressBar(BossEvent bossEvent) {
            int phase = getCurrentPhase();
            if (prevPhase != phase) {
                BossEvent.BossBarColor color = getPhaseColor(phase);
                bossEvent.setColor(color);
                prevPhase = phase;
            }
            bossEvent.setProgress(calculateProgress(phase));
        }

        private float calculateProgress(int phase) {
            float maxPhaseHealth = phaseHealthArray[phase - 1];
            float currentPhaseHealth = getCurrentPhaseHealth();

            return currentPhaseHealth / maxPhaseHealth;
        }
    }

    public enum ArmPose {
        NORMAL(0),
        RAISE_RIGHT(1),
        RAISE_LEFT(2),
        RAISE_BOTH(3);

        final int id;

        public static ArmPose byId(int id) {
            return switch (id) {
                case 1 -> RAISE_RIGHT;
                case 2 -> RAISE_LEFT;
                case 3 -> RAISE_BOTH;
                default -> NORMAL;
            };
        }

        ArmPose(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
